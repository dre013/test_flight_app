package by.dre.je.jdbc.servlet;

import by.dre.je.jdbc.dto.UserDto;
import by.dre.je.jdbc.service.UserService;
import by.dre.je.jdbc.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

import static by.dre.je.jdbc.utils.UrlPath.LOGIN;

@WebServlet(LOGIN)
public class LoginServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("login")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.login(req.getParameter("email"), req.getParameter("password"))
                .ifPresentOrElse(userDto -> onLoginSucces(userDto, req, resp),
                        () -> onLoginFailed(req, resp));
    }

    @SneakyThrows
    private void onLoginFailed(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/login?error&email=" + req.getParameter("email"));
    }

    @SneakyThrows
    private void onLoginSucces(UserDto userDto, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("user", userDto);
        resp.sendRedirect("/flights");
    }
}
