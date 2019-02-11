/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import Entidades.Module;

/**
 *
 * @author frankzapeta
 */
/*
objeto de tipo modulo configuracion e item de habilitado/deshabilitado
*/
public class UserModuleItemEnable {
    
     public Module module;
     public String item;
     public boolean config;

    public UserModuleItemEnable(Module module, String item, boolean config) {
        this.module = module;
        this.item = item;
        this.config = config;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isConfig() {
        return config;
    }

    public void setConfig(boolean config) {
        this.config = config;
    }
     
    
}
