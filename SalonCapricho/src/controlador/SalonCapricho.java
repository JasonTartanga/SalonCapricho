package controlador;

import modelo.DAO;
import modelo.DAOImplementacion;
import vista.VMain;

/**
 *
 * @author xDoble_Jx
 */
public class SalonCapricho {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DAO dao = new DAOImplementacion();
        VMain vMain = new VMain(dao);
        vMain.setVisible(true);
    }

}
