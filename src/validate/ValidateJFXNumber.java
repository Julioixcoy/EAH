/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validate;

import com.jfoenix.controls.JFXTextField;

/**
 *
 * @author Julio Fernando Ixcoy
 */
/**
 * clase que deriva de JFTextField para numeros 
 * la otra clase en este paquete es para Textfield
 * 
 */
public class ValidateJFXNumber extends JFXTextField{

    public ValidateJFXNumber() {
    }
    
    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[0-9]") || text.isEmpty() ||text.matches("[##.00]")) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement); //To change body of generated methods, choose Tools | Templates.
    }
}
