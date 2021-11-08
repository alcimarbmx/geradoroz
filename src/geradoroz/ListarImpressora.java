/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geradoroz;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

/**
 *
 * @author alcimar
 */
public class ListarImpressora {

    public void main() {
        PrintService[] ps = null;
        ps = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService psi : ps) {
            System.out.println(psi.getName());
        }
    }
}
