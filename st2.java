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
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

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


@Typed(PaymentService.class)
@ApplicationScoped
public class CreditService extends AbstractTxService implements PaymentService {...}


@Qualifier
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, TYPE})
public @interface Distance {}

@Qualifier
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, TYPE})
public @interface FIFO {}

@ApplicationScoped
@Distance
public class InventoryReserverByDistance implements InventryReserver {...}

@ApplicationScoped
@FIFO
public class InventoryReserverByFIFO implements InventryReserver {...}

@Inject
@Distance
InventryReserver reserver;

@Qualifier
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, TYPE})
public @interface Express {}

@ApplicationScoped
@Express
@FIFO
public class InventoryReserverExpressByFIFO implements InventryReserver {...}

@Inject
@Express
@FIFO
InventryReserver reserver;



@Qualifier
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, TYPE})
public @interface Reserver {
  ReserverStrategy value();
}

public enum ReserverStrategy {
  DISTANCE, FIFO
}

@ApplicationScoped
@Reserver(DISTANCE)
public class InventoryReserverByDistance implements InventryReserver {...}

@ApplicationScoped
@Reserver(FIFO)
public class InventoryReserverByFIFO implements InventryReserver {...}

@Reserver(FIFO)
InventoryReserver reserver;

