/*
 *Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *WSO2 Inc. licenses this file to you under the Apache License,
 *Version 2.0 (the "License"); you may not use this file except
 *in compliance with the License.
 *You may obtain a copy of the License at
 *
 *http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing,
 *software distributed under the License is distributed on an
 *"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *KIND, either express or implied.  See the License for the
 *specific language governing permissions and limitations
 *under the License.
 */

package org.wso2.carbon.identity.application.common.model;

import java.io.Serializable;
import java.util.*;

import org.apache.axiom.om.OMElement;

public class FederatedAuthenticatorConfig implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6562346450443426344L;

    protected String name;
    protected String displayName;
    protected boolean enabled;
    protected Property[] properties = new Property[0];

    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * 
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 
     * @return
     */
    public boolean isValid() {
        return true;
    }

    /**
     * 
     * @return
     */
    public Property[] getProperties() {
        return properties;
    }

    /**
     * 
     * @param properties
     */
    public void setProperties(Property[] properties) {
        if(properties == null){
            return;
        }
        Set<Property> propertySet = new HashSet<Property>(Arrays.asList(properties));
        this.properties = propertySet.toArray(new Property[propertySet.size()]);
    }

    /**
     * @return
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /*
     * <FederatedAuthenticatorConfig> <Name></Name> <DisplayName></DisplayName>
     * <IsEnabled></IsEnabled> <Properties></Properties> </FederatedAuthenticatorConfig>
     */

    public static FederatedAuthenticatorConfig build(OMElement federatedAuthenticatorConfigOM) {

        if (federatedAuthenticatorConfigOM == null) {
            return null;
        }

        FederatedAuthenticatorConfig federatedAuthenticatorConfig = new FederatedAuthenticatorConfig();

        Iterator<?> iter = federatedAuthenticatorConfigOM.getChildElements();

        while (iter.hasNext()) {
            OMElement element = (OMElement) (iter.next());
            String elementName = element.getLocalName();

            if (elementName.equals("Name")) {
                federatedAuthenticatorConfig.setName(element.getText());
            } else if (elementName.equals("DisplayName")) {
                federatedAuthenticatorConfig.setDisplayName(element.getText());
            } else if (elementName.equals("IsEnabled")) {
                federatedAuthenticatorConfig.setEnabled(Boolean.parseBoolean(element.getText()));
            } else if (elementName.equals("Properties")) {
                Iterator<?> propertiesIter = element.getChildElements();
                ArrayList<Property> propertiesArrList = new ArrayList<Property>();

                if (propertiesIter != null) {
                    while (propertiesIter.hasNext()) {
                        OMElement propertiesElement = (OMElement) (propertiesIter.next());
                        propertiesArrList.add(Property.build(propertiesElement));
                    }
                }

                if (propertiesArrList.size() > 0) {
                    Property[] propertiesArr = propertiesArrList.toArray(new Property[0]);
                    federatedAuthenticatorConfig.setProperties(propertiesArr);
                }
            }
        }

        return federatedAuthenticatorConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FederatedAuthenticatorConfig)) return false;

        FederatedAuthenticatorConfig that = (FederatedAuthenticatorConfig) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
