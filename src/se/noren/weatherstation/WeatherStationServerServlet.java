package se.noren.weatherstation;
import java.io.IOException;

import javax.servlet.http.*;

import org.springframework.web.servlet.HttpServletBean;

@SuppressWarnings("serial")
public class WeatherStationServerServlet extends HttpServletBean {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
