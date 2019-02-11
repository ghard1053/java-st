// cdi

import javax.enterprise.context.SessionScoped;
import java.util.concurrent.atomic.AtomicLong;
import javax.inject.Named;
import javax.io.Serializable;

@Named
@SessionScoped
public class RequestCounter implements Serializable {
  private static final long serialVersionUID = 1L;
  private final AtomicLong count = new AtomicLong();

  public void increment() {
    count.incrementAndGet();
  }

  public long now() {
    return count.get();
  }
}

//---------------------

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StrongMessageService implements MessageService {
  @Override
  public String create(String name) {
    return "*** " + name + " ***";
  }
}

//---------------------

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet("/echo")
public class EchoServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Inject
  RequestCounter counter;

  @Inject
  MessageService msgService;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws IOException, ServletException {
      
      String name = (String) req.getParameter("name");

      String message = msgService.create(name);

      req.setAttribute("title", message);

      counter.increment();
      req.getRequestDispatcher("/WEB-INF/echo.jsp").forward(req, res);
    }
}
