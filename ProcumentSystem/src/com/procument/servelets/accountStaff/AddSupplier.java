package com.procument.servelets.accountStaff;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.procument.models.Supplier;
import com.procument.models.User;
import com.procument.services.SupplierServicesImpl;
import com.procument.services.SupplierServicesInt;

/**
 * Servlet implementation class AddSupplier
 */
@WebServlet("/AddSupplier")
public class AddSupplier extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddSupplier() {
        super();
        // TODO Auto-generated constructor stub
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		SupplierServicesInt supplierServicesInt = new SupplierServicesImpl();
		ArrayList<Supplier> suppliers = supplierServicesInt.get_AllSuppliers();
		
		request.setAttribute("suppliers", suppliers);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("add_supliers.jsp");
		dispatcher.forward(request, response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final Logger LOGGER = Logger.getLogger(AddSupplier.class.getName());
		ArrayList<Supplier> suppliers = null ;
		
		try {
			SupplierServicesInt supplierServicesInt = new SupplierServicesImpl();
			Supplier supplier = new Supplier();
			
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			
			supplier.setSup_name(request.getParameter("name"));
			supplier.setComp_name(request.getParameter("compName"));
			supplier.setSup_contact(Integer.parseInt(request.getParameter("contact")));
			supplier.setSup_email(request.getParameter("email"));
			supplier.setSup_addby(user.getId());
			supplier.setSup_level(Integer.parseInt(request.getParameter("level")));
			
			if(request.getParameter("approve")!= null) {
				supplier.setApprove(true);
			}
			else {
				supplier.setApprove(false);
			}
			
			supplierServicesInt.add_supplier(supplier);
			suppliers = supplierServicesInt.get_AllSuppliers();
			
		}catch(Exception ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage());
		}
		
		request.setAttribute("suppliers", suppliers);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("add_supliers.jsp");
		dispatcher.forward(request, response);
		
//		doGet(request, response);
	}

}
