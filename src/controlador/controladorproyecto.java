package controlador;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;

import modelo.conexion;
import modelo.crud;
import objetos.contenidos;
import objetos.usuarios;

/**
 * Servlet implementation class controladorproyecto
 */
@WebServlet("/controladorproyecto")
public class controladorproyecto extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		int action = Integer.parseInt(request.getParameter("action"));
		String clave;
		String login;
		switch (action) {
		case 1:

			// iniciar sesion
			login = request.getParameter("user");
			clave = request.getParameter("passwd");
			
			try {
				iniciases(request, response, login, clave);
			} catch (ClassNotFoundException | SQLException | ServletException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case 2: // REGISTRAR
			System.out.println("Entro al controlador");
			login = request.getParameter("nombre");
			clave = request.getParameter("clave");
			usuarios u = new usuarios();

			u.setContrasena((String) (request.getParameter("clave")));
			u.setCorreo((String) request.getParameter("correo"));
			u.setNombre((String) request.getParameter("nombre"));

		
			try {
				

				nuevoUsu(request, response, u);
			} catch (ClassNotFoundException f) {
				// TODO Auto-generated catch block
				f.printStackTrace();
				
			}
			break;
		case 3: //Devuelve a la pantalla de seleccion de los contenidos seleccionados
			String requerido = (request.getParameter("demandado"));
			crud ai = new crud();
			ArrayList<contenidos> listado = null;
			switch (requerido) {
			case "musica":
				listado = ai.listaMusica();
				break;

			case "peliculas":
				listado = ai.listaPelis();
				break;

			case "series":
				listado = ai.listaSeries();
				break;

			}
			request.setAttribute("listaC", listado);
			request.setAttribute("requerido", requerido);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/expositor.jsp");
			dispatcher.forward(request, response);
			break;
		// Volver al index desde cualquier pag

		case 4:
			devuelvemain(request, response);

			break;
		case 5:	//Cierro la sesion
			HttpSession session = request.getSession();
			String usu = (String) session.getAttribute("name");
			session.setAttribute("name", usu);
			try {
				devuelvePerfil(request, response, usu);
			} catch (ClassNotFoundException | ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 6: //Actualizando los datos
			usuarios us = new usuarios();
			us.setId_usuario(Integer.parseInt((request.getParameter("id"))));
			us.setContrasena((String) (request.getParameter("clave")));
			us.setCorreo((String) request.getParameter("correo"));
			us.setNombre((String) request.getParameter("nombre"));
			System.out.println(us.toString());
			try {
				actualizaDatos(request, response, us);
			} catch (ClassNotFoundException | ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}
	
	//Actualiza los datos que el usuario desea cambiar
	private void actualizaDatos(HttpServletRequest request, HttpServletResponse response, usuarios us) throws ServletException, IOException, ClassNotFoundException {
		int ok = 0;
		crud ai=new crud();
	if(!us.getNombre().isEmpty() && us.getNombre()!=null) {
		ok=ai.actualizaNombre(us);
	
	}
	if(!us.getCorreo().isEmpty() && us.getContrasena()!=null) {
		ok=ai.actualizaCorreo(us);
		
	}
	if(!us.getContrasena().isEmpty() && us.getContrasena()!=null) {
		ok=ai.actualizaContrasena(us);
		
	}
	request.setAttribute("ok", String.valueOf(ok));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/registrocompleto.jsp");
		dispatcher.forward(request, response);
	}

	private void devuelvePerfil(HttpServletRequest request, HttpServletResponse response, String usu) throws ServletException, IOException, ClassNotFoundException {
		crud ai = new crud();
		ArrayList<usuarios> listaU;
		listaU=ai.identificarP(usu);
		request.setAttribute("listaU", listaU);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/perfiles.jsp");
		dispatcher.forward(request, response);
	}
	
	//Devuelve al main desde cualquier otra pagina (evita bugs)
	private void devuelvemain(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<contenidos> videos, series;
		crud ai1 = new crud();
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("name");
		session.setAttribute("name", name);
		// Lista de series y lista del contenido
		videos = ai1.listaContenido();
		series = ai1.listaSeries();

		// Sesion del usuario

		// Saco 15 recomendaciones aleatorias
		int[] nums = ai1.listaRecomendaciones(videos);
		int[] recoserie = ai1.listaRecomendaciones(series);
	
		// Envio todos los parametros para el main

		request.setAttribute("listaC", videos);
		request.setAttribute("listaS", series);
		request.setAttribute("nums", nums);
		request.setAttribute("recoserie", recoserie);

		// Redirijo al main

		RequestDispatcher dispatcher = request.getRequestDispatcher("/mainredirect.jsp");
		dispatcher.forward(request, response);
	}
 
	//Inicia sesion
	private void iniciases(HttpServletRequest request, HttpServletResponse response, String login, String clave)
			throws ClassNotFoundException, SQLException, ServletException, IOException {
		ArrayList<usuarios> listaU;
		ArrayList<contenidos> videos, series;
		crud ai = new crud();
		listaU=ai.identificarP(login, clave);
		// Lista de series y lista del contenido
		videos = ai.listaContenido();
		series = ai.listaSeries();

		// Sesion del usuario
		HttpSession session = request.getSession();

		session.setAttribute("name", login);
		// Miro si el user esta en la db
	

		// Saco 15 recomendaciones aleatorias
		int[] nums = ai.listaRecomendaciones(videos);
		int[] recoserie = ai.listaRecomendaciones(series);

		// Envio todos los parametros para el main
		request.setAttribute("listaU", listaU);
		request.setAttribute("listaC", videos);
		request.setAttribute("listaS", series);
		request.setAttribute("nums", nums);
		request.setAttribute("recoserie", recoserie);

		// Redirijo al main

		RequestDispatcher dispatcher = request.getRequestDispatcher("/main.jsp");
		dispatcher.forward(request, response);

	}
//Crea un nuevousuario
	private void nuevoUsu(HttpServletRequest request, HttpServletResponse response, usuarios u)
			throws ServletException, IOException, ClassNotFoundException {
		
		String user= u.getNombre();
		
		int ok;

		crud ai = new crud();
		ok = ai.nuevoUsu(u);

		request.setAttribute("ok", String.valueOf(ok));
	
		RequestDispatcher dispatcher = request.getRequestDispatcher("/registrocompleto.jsp");
		dispatcher.forward(request, response);
	}

}
