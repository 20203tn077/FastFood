package mx.edu.utez.fastfood.fastfood.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.edu.utez.fastfood.fastfood.dao.CategoryDao;
import mx.edu.utez.fastfood.fastfood.dao.DishDao;
import mx.edu.utez.fastfood.fastfood.dao.IngredientDao;
import mx.edu.utez.fastfood.fastfood.exception.ValidationException;
import mx.edu.utez.fastfood.fastfood.model.Category;
import mx.edu.utez.fastfood.fastfood.model.Dish;
import mx.edu.utez.fastfood.fastfood.model.Ingredient;
import mx.edu.utez.fastfood.fastfood.service.ValidationService;
import mx.edu.utez.fastfood.fastfood.util.DishGson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "DishServlet", value = "/Platillos")
public class DishServlet extends HttpServlet {
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;
        switch (request.getParameter("action") != null ? request.getParameter("action") : "") {
            case "index":
                index(request, response);
            case "find":
                find(request, response);
            case "create":
                create(request, response);
                break;
            case "update":
                update(request, response);
                break;
            case "delete":
                delete(request, response);
                break;
            default:
                index(request, response);
        }
    }

    private void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DishDao dishDao = new DishDao();
        CategoryDao categoryDao = new CategoryDao();
        IngredientDao ingredientDao = new IngredientDao();
        try {
            request.setAttribute("dishes", dishDao.findAll());
            request.setAttribute("categories", categoryDao.findAll());
            request.setAttribute("ingredients", ingredientDao.findAll());
        } catch (Exception e) {
            request.setAttribute("success", false);
            request.setAttribute("message", "Ocurrió un error inesperado");
        } finally {
            redirect("/views/dishes.jsp");
        }
    }

    private void find(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> responseData = new HashMap<>();
        try {
            DishDao dishDao = new DishDao();
            long id = ValidationService.validateNumber(Long.valueOf(request.getParameter("id")), "ID", true, null, null, null);
            Dish dish = dishDao.findById(id);
            if (dish == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                responseData.put("message", "No se encontró el platillo indicado");
            } else responseData.put("dish", dish);
        } catch (ValidationException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseData.put("message", e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseData.put("message", "Ocurrió un error inesperado");
        } finally {
            sendJson(responseData);
        }
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DishDao dishDao = new DishDao();
            List<Ingredient> ingredients = new ArrayList<>();
            for (String ingredientId : ValidationService.validateCheckbox(request.getParameterValues("ingredients"), "Ingredientes", true, null, null)) {
                ingredients.add(new Ingredient(Long.parseLong(ingredientId)));
            }
            Dish dish = new Dish(
                    ValidationService.validateText(request.getParameter("name"), "Nombre", true, null, 45, null),
                    ValidationService.validateText(request.getParameter("description"), "Descripción", false, null, 100, null),
                    ValidationService.validateNumber(Double.valueOf(request.getParameter("price")), "Precio", true, null, 0d, null),
                    new Category(ValidationService.validateNumber(Long.valueOf(request.getParameter("categoryId")), "Categoría", true, null, null, null)),
                    ingredients
            );
            boolean success = dishDao.create(dish);
            request.setAttribute("success", success);
            request.setAttribute("message", success ? "El platillo se registró exitosamente" : "El platillo no pudo ser creado");
        } catch (ValidationException e) {
            request.setAttribute("success", false);
            request.setAttribute("message", e.getMessage());
        } catch (Exception e) {
            request.setAttribute("success", false);
            request.setAttribute("message", "Ocurrió un error inesperado");
        } finally {
            index(request, response);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DishDao dishDao = new DishDao();
            long id = ValidationService.validateNumber(Long.valueOf(request.getParameter("id")), "ID", true, null, null, null);
            if (!dishDao.existsById(id)) {
                List<Ingredient> ingredients = new ArrayList<>();
                for (String ingredientId : ValidationService.validateCheckbox(request.getParameterValues("ingredients"), "Ingredientes", true, null, null)) {
                    ingredients.add(new Ingredient(Long.parseLong(ingredientId)));
                }
                Dish dish = new Dish(
                        id,
                        ValidationService.validateText(request.getParameter("name"), "Nombre", true, null, 45, null),
                        ValidationService.validateText(request.getParameter("description"), "Descripción", false, null, 100, null),
                        ValidationService.validateNumber(Double.valueOf(request.getParameter("price")), "Precio", true, null, 0d, null),
                        new Category(ValidationService.validateNumber(Long.valueOf(request.getParameter("categoryId")), "Categoría", true, null, null, null)),
                        ingredients
                );
                boolean success = dishDao.update(dish);
                request.setAttribute("success", success);
                request.setAttribute("message", success ? "El platillo se actualizó exitosamente" : "El platillo no pudo ser actualizado");
            } else {
                request.setAttribute("success", true);
                request.setAttribute("message", "");
            }
        } catch (ValidationException e) {
            request.setAttribute("success", false);
            request.setAttribute("message", e.getMessage());
        } catch (Exception e) {
            request.setAttribute("success", false);
            request.setAttribute("message", "Ocurrió un error inesperado");
        } finally {
            index(request, response);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DishDao dishDao = new DishDao();
            long id = ValidationService.validateNumber(Long.valueOf(request.getParameter("id")), "ID", true, null, null, null);
            if (!dishDao.existsById(id)) {
                request.setAttribute("success", false);
                request.setAttribute("message", "No se encontró el platillo indicado");
            } else {
                boolean success = new DishDao().delete(id);
                request.setAttribute("success", success);
                request.setAttribute("message", success ? "El platillo se desactivó exitosamente" : "El platillo no pudo ser desactivado");
            }
        } catch (ValidationException e) {
            request.setAttribute("success", false);
            request.setAttribute("message", e.getMessage());
        } catch (Exception e) {
            request.setAttribute("success", false);
            request.setAttribute("message", "Ocurrió un error inesperado");
        } finally {
            index(request, response);
        }
    }

    private void redirect(String url) throws ServletException, IOException {
        request.getRequestDispatcher(url).forward(request, response);
    }

    private void sendJson(Object obj) throws IOException {
        System.out.println(obj);
        response.setContentType("application/json");
        response.getWriter().write(DishGson.getGson().toJson(obj));
    }
}
