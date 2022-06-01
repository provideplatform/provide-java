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

package services.provide.dao;

import java.util.Map;

public class Network {
    private String id;
    private String created_at;
    private String application_id;
    private String user_id;
    private String name;
    private String description;
    private boolean is_production;
    private boolean cloneable;
    private boolean enabled;
    private String chain_id;
    private String sidechain_id;
    private String network_id;
    private String stats;
    private Map<String, String> config;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getApplication_id() {
        return application_id;
    }

    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIs_production() {
        return is_production;
    }

    public void setIs_production(boolean is_production) {
        this.is_production = is_production;
    }

    public boolean isCloneable() {
        return cloneable;
    }

    public void setCloneable(boolean cloneable) {
        this.cloneable = cloneable;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getChain_id() {
        return chain_id;
    }

    public void setChain_id(String chain_id) {
        this.chain_id = chain_id;
    }

    public String getSidechain_id() {
        return sidechain_id;
    }

    public void setSidechain_id(String sidechain_id) {
        this.sidechain_id = sidechain_id;
    }

    public String getNetwork_id() {
        return network_id;
    }

    public void setNetwork_id(String network_id) {
        this.network_id = network_id;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return "Network [application_id=" + application_id + ", chain_id=" + chain_id + ", cloneable=" + cloneable
                + ", config=" + config + ", created_at=" + created_at + ", description=" + description + ", enabled="
                + enabled + ", id=" + id + ", is_production=" + is_production + ", name=" + name + ", network_id="
                + network_id + ", sidechain_id=" + sidechain_id + ", stats=" + stats + ", user_id=" + user_id + "]";
    }

    

    
}