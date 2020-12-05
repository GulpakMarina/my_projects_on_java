package servlets;

import crud.CuisineRepository;
import crud.DefaultRepository;
import model.Cuisine;
import service.CuisineService;
import utility.IncorrectValueException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "CuisineServlet",
        urlPatterns = "/cuisine")
public class CuisineServlet extends HttpServlet {




    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Cuisine> cuisineList=CuisineRepository.getAll();
        Cuisine cuisine=new Cuisine();

        request.setAttribute("cuisines",cuisineList);
        request.setAttribute("cuisineEmpty",cuisine);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/cuisine.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        switch (request.getParameter("type")){
            case "add": addNewCuisine(request); break;
            case "edit": editCuisine(request); break;
            case "delete": deleteCuisine(request);
        }

        doGet(request,response);
    }

    private void addNewCuisine(HttpServletRequest request){
        try {
            Cuisine cuisine=new Cuisine.Builder().setTitle(request.getParameter("cuisineTitle")).build();
            CuisineRepository.add(cuisine);
        } catch (IncorrectValueException e) {
            e.printStackTrace();
            request.setAttribute("error",e.getMessage());
        }
    }

    private void editCuisine(HttpServletRequest request){
        try {
            Cuisine cuisine=new Cuisine.Builder().setTitle(request.getParameter("cuisineTitle")).build();
            CuisineRepository.update(Integer.parseInt(request.getParameter("cuisineId")),cuisine);
        } catch (IncorrectValueException e) {
            e.printStackTrace();
            request.setAttribute("error",e.getMessage());
        }
    }

    private void deleteCuisine(HttpServletRequest request){
        CuisineRepository.delete(Integer.parseInt(request.getParameter("cuisineId")));
    }
}