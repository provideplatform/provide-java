/*
 * Copyright 2017-2022 Provide Technologies Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package services.provide.microservices;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


import services.provide.client.ApiClient;
import services.provide.dao.Application;
import services.provide.dao.KYC;
import services.provide.dao.Token;
import services.provide.dao.User;
import services.provide.helper.ProvideServicesException;

public class Ident {
    private static final String DEFAULT_HOST = "ident.provide.services";
    private ApiClient client = null;
    private ObjectMapper mapper = null;


    public static Ident init(String token)
    {
        return new Ident(token);
    }

    private Ident(String token)
    {
        String schema = System.getenv("IDENT_API_SCHEME");
        String host = System.getenv("IDENT_API_HOST");
        host = (host == null ? DEFAULT_HOST : host);

        this.client = ApiClient.init(schema, host, token);

        // Set ObjectMapper
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Application createApplication(String app_name, String network_id, String description, boolean hidden) throws ProvideServicesException
    {
        if (app_name == null) throw new ProvideServicesException("ERROR: createApplication app_name cannot be null;");
        if (network_id == null) throw new ProvideServicesException("ERROR: createApplication network_id cannot be null;");
        Application application = null;
        Application newApplication = new Application();
        newApplication.setName(app_name);
        newApplication.setDescription(description);
        newApplication.setHidden(hidden);
        newApplication.setConfig(newApplication.new Config(network_id));
        try {

            ArrayList<String> result = client.post("applications", null);
            if (!result.get(0).equals("201"))
                throw new ProvideServicesException("ERROR: createApplication; " + result.get(1));
            application = mapper.readValue(result.get(1), Application.class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return application;
    }

    public void updateApplication(Application application) throws ProvideServicesException
    {
        if (application == null) throw new ProvideServicesException("ERROR: updateApplication application cannot be null;");
        try {
            Writer jsonWriter = new StringWriter();
            mapper.writeValue(jsonWriter, application);
            jsonWriter.flush();
            ArrayList<String> result = client.put("applications/"+application.getId(), jsonWriter.toString());
            if (!result.get(0).equals("204")) throw new ProvideServicesException("ERROR: updateApplication; " + result.get(1));
        } catch(Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }
    }

    public Application[] fetchApplications(boolean hidden, String network_id) throws ProvideServicesException{
        Application[] applications = null;
        try {
            ArrayList<String> result = client.get("applications?hidden="+hidden+(network_id == null ? "" : "&network_id=" + network_id), null);
            if (!result.get(0).equals("200")) throw new ProvideServicesException("ERROR: fetchApplications; " + result.get(1));
            applications = mapper.readValue(result.get(1), Application[].class);
        } catch(Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return applications;
    }

    public Application fetchApplicationDetails(String app_id) throws ProvideServicesException
    {
        if (app_id== null)
            throw new ProvideServicesException("ERROR: fetchApplicationDetails app_id cannot be null;");
        Application application = null;
        try {
            ArrayList<String> result = client.get("applications/"+app_id, null);
            if (!result.get(0).equals("200"))
                throw new ProvideServicesException("ERROR: fetchApplicationDetails; " + result.get(1));
            application = mapper.readValue(result.get(1), Application.class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return application;
    }

    public void deleteApplication(String app_id) throws ProvideServicesException {
        if (app_id == null)
            throw new ProvideServicesException("ERROR: deleteApplication app_id cannot be null");
        try {
            ArrayList<String> result = client.delete("applications/" + app_id, null);
            if (!result.get(0).equals("204"))
                throw new ProvideServicesException("ERROR: deleteApplication; " + result.get(1));
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }
    }

    public Token[] fetchApplicationTokens(String app_id) throws ProvideServicesException
    {
        if (app_id == null)
            throw new ProvideServicesException("ERROR: fetchApplicationTokens app_id cannot be null;");
        Token[] tokens = null;
        try {
            ArrayList<String> result = client.get("applications/"+app_id+"/tokens", null);
            if (!result.get(0).equals("200"))
                throw new ProvideServicesException("ERROR: Failed to fetchApplicationTokens; " + result.get(1));
            tokens = mapper.readValue(result.get(1), Token[].class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return tokens;
    }

    public KYC[] fetchKycApplications() throws ProvideServicesException {
        KYC[] kycs = null;
        try {
            ArrayList<String> result = client.get("kyc_applications", null);
            if (!result.get(0).equals("200"))
                throw new ProvideServicesException("ERROR: Failed to fetchKycApplications; " + result.get(1));
            kycs = mapper.readValue(result.get(1), KYC[].class);
        } catch(Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return kycs;
    }


    public KYC createKycApplication(KYC kyc) throws ProvideServicesException {
        if (kyc == null)
            throw new ProvideServicesException("ERROR: createKycApplication kyc cannot be null;");
        KYC newKyc = null;
        Writer jsonWriter = new StringWriter();
        try {
            mapper.writeValue(jsonWriter, kyc);
            jsonWriter.flush();
            ArrayList<String> result = client.post("kyc_applications", jsonWriter.toString());
            if (!result.get(0).equals("201"))
                throw new ProvideServicesException("ERROR: createToken; " + result.get(1));
            newKyc = mapper.readValue(result.get(1), KYC.class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return newKyc;
    }

    public void updateKycApplication(KYC kyc) throws ProvideServicesException {
        if (kyc == null)
            throw new ProvideServicesException("ERROR: updateKycApplication kyc cannot be null;");
        try {
            Writer jsonWriter = new StringWriter();
            mapper.writeValue(jsonWriter, kyc);
            jsonWriter.flush();
            ArrayList<String> result = client.put("kyc_applications/" + kyc.getId(), jsonWriter.toString());
            if (!result.get(0).equals("204"))
                throw new ProvideServicesException("ERROR: updateKycApplication; " + result.get(1));
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }
    }

    // TODO: code for fetchKycApplicationDetails

    // TODO: code for KYC Documents

    public Token[] fetchTokens() throws ProvideServicesException {
        Token[] tokens = null;
        try {
            ArrayList<String> result = client.get("tokens", null);
            if (!result.get(0).equals("200"))
                throw new ProvideServicesException("ERROR: Failed to fetch connectors;");
            tokens = mapper.readValue(result.get(1), Token[].class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return tokens;
    }

    
    
    public Token createToken(String token_name) throws ProvideServicesException
    {
        if (token_name == null) throw new ProvideServicesException("ERROR: createToken token_name cannot be null;");
        Token token = null;
        try {
            ArrayList<String> result = client.post("tokens", "{\"name\":\"" + token_name + "\"}");
            if (!result.get(0).equals("201")) throw new ProvideServicesException("ERROR: createToken; " + result.get(1));
            token = mapper.readValue(result.get(1), Token.class);
        } catch(Exception e)
        {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return token;
    }
    

    public void deleteToken(String token_id) throws ProvideServicesException
    {
        if (token_id == null) throw new ProvideServicesException("ERROR: deleteToken token_id cannot be null");
        try {
            ArrayList<String> result = client.delete("tokens/"+token_id, null);
            if (!result.get(0).equals("204")) throw new ProvideServicesException("ERROR: deleteToken " + token_id +"; " + result.get(1));
        } catch(Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }
    }

    public User createUser(User user) throws ProvideServicesException
    {
        if (user == null)
            throw new ProvideServicesException("ERROR: createUser user cannot be null;");
        User newUser = null;
        try {
            Writer jsonWriter = new StringWriter();
            mapper.writeValue(jsonWriter, user);
            jsonWriter.flush();
            ArrayList<String> result = client.post("users", jsonWriter.toString());
            if (!result.get(0).equals("201"))
                throw new ProvideServicesException("ERROR: createUser; " + result.get(1));
            newUser = mapper.readValue(result.get(1), User.class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }
        return newUser;
    }

    public User[] fetchUsers() throws ProvideServicesException {
        User[] users = null;
        try {
            ArrayList<String> result = client.get("users", null);
            if (!result.get(0).equals("200")) throw new ProvideServicesException("ERROR: fetchUsers; " + result.get(1));
            users = mapper.readValue(result.get(1), User[].class);
        } catch(Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return users;
    }

    public User fetchUserDetails(String userId) throws ProvideServicesException
    {
        if (userId == null) throw new ProvideServicesException("ERROR: fetchUserDetails userId cannot be null;");
        User user = null;
        try {
            ArrayList<String> result = client.get("users/"+userId, null);
            if (!result.get(0).equals("200")) throw new ProvideServicesException("ERROR: fetchUserDetails; " + result.get(1));
            user = mapper.readValue(result.get(1), User.class);
        } catch(Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }
        return user;
    }

    public void updateUser(User user) throws ProvideServicesException
    {
        if (user == null) throw new ProvideServicesException("ERROR: updateUser user cannot be null;");
        try {
             Writer jsonWriter = new StringWriter();
            mapper.writeValue(jsonWriter, user);
            jsonWriter.flush();
            ArrayList<String> result = client.put("users/" + user.getId(), jsonWriter.toString());
            if (!result.get(0).equals("204")) throw new ProvideServicesException("ERROR: updateUser; " + result.get(1));
        } catch(Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }
    }

    public void deleteUser(String userId) throws ProvideServicesException
    {
        if (userId == null) throw new ProvideServicesException("ERROR: deleteUser userId cannot be null");
        try {
            ArrayList<String> result = client.delete("users/"+userId, null);
            if (!result.get(0).equals("204")) throw new ProvideServicesException("ERROR: deleteUser; " + result.get(1));
        } catch(Exception e)
        {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }
    }
}