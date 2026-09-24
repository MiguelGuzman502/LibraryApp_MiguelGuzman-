
package org.libreria.DAO;


import java.util.ArrayList;
import org.libreria.model.LineaFactura;

public interface FacturaDAO {
    ArrayList<LineaFactura> buscarFactura(int noVenta);
}