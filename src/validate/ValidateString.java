/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validate;

import javafx.scene.control.TextField;

/**
 *
 * @author Julio Fernando Ixcoy
 */
/**
 * valida texto, solo caracteres
 * 

*/
public class ValidateString extends     TextField{

    public ValidateString() {
        this.setPromptText("Ingrese Solo Letras");
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[a-zA-Z]") || text.isEmpty()) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement); //To change body of generated methods, choose Tools | Templates.
    }
    
}
