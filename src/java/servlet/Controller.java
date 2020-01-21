/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojos.Categoria;
import pojos.Producto;
import pojos.Punto;
import util.HibernateUtil;

/**
 *
 * @author alfredolop
 */
public class Controller extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private int i;
    private int estrellas;
    private Query q;
    private Session singleton;
    private List<Categoria> categorias;
    private Categoria categoria;
    private Set productosSet;
    private List<Producto> productos;
    private Producto producto;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher;
        String op = request.getParameter("op");
        singleton = HibernateUtil.getSessionFactory().openSession();
        if (op.equals("inicio")) {
            q = singleton.createQuery("from Categoria");
            categorias = q.list();
            session.setAttribute("categorias", categorias);
            dispatcher = request.getRequestDispatcher("home.jsp");
            dispatcher.forward(request, response);

        } else if (op.equals("dameproductos")) {
            i = Integer.parseInt(request.getParameter("i"));
            categorias = (List<Categoria>) session.getAttribute("categorias");
            categoria = categorias.get(i);
            productosSet = categoria.getProductos();
            Iterator productositer = productosSet.iterator();
            productos = new ArrayList<Producto>();
            while (productositer.hasNext()){
                productos.add((Producto) productositer.next());
            }
            session.setAttribute("productos", productos);
            session.setAttribute("categoria", categoria.getNombre());
            dispatcher = request.getRequestDispatcher("platos.jsp");
            dispatcher.forward(request, response);		
        } else  if (op.equals("detail")) {
            i = Integer.parseInt(request.getParameter("i"));
            productos = (ArrayList<Producto>) session.getAttribute("productos");
            producto = productos.get(i);
            Set puntosSet = producto.getPuntos();
            estrellas = 0;
            if (puntosSet.size()>0){
                Iterator iteratorPun = puntosSet.iterator();
                int suma = 0;
                while (iteratorPun.hasNext()){
                    suma+=((Punto)iteratorPun.next()).getPuntos();
                }
                estrellas = Math.round(suma / puntosSet.size());
            }
            session.setAttribute("producto", producto);
            session.setAttribute("estrellas", estrellas);
            dispatcher = request.getRequestDispatcher("detail.jsp");
            dispatcher.forward(request, response);		
        } else  if (op.equals("rating")) {
            producto = (Producto)session.getAttribute("producto");
            String puntos = request.getParameter("rating");
            Punto punto = new Punto();
            punto.setId(1);
            punto.setProducto(producto);
            punto.setPuntos(Short.valueOf(puntos));
            Transaction tran = singleton.beginTransaction();
            singleton.persist(punto);
            tran.commit();
            // Calcular las nuevas estrellas.
            estrellas = 0;
            q = singleton.createQuery("select round(avg(puntos)) from Punto where producto.id = "+producto.getId());
            estrellas = ((List<Double>)q.list()).get(0).intValue();
            session.setAttribute("estrellas", estrellas);
            dispatcher = request.getRequestDispatcher("detail.jsp");
            dispatcher.forward(request, response);		
        }
		
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
