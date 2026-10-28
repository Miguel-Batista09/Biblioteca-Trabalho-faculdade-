package br.edu.biblioteca.controller;

import br.edu.biblioteca.dao.LivroDao;
import br.edu.biblioteca.modelo.Livro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// Mapeamento na raiz para interceptar todas as requisições (Trabalho Prático 03)
@WebServlet("/")
public class BibliotecaController extends HttpServlet {

    private LivroDao dao;

    // Inicialização única do DAO (Trabalho Prático 03)
    @Override
    public void init() throws ServletException {
        this.dao = new LivroDao();
    }

    // Unificação de Requisições (Trabalho Prático 03)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();
        System.out.println("Caminho: " + path);

        // Roteamento de Ações (Trabalho Prático 03)
        switch (path) {
            case "/new":
            case "/form":
                showForm(request, response);
                break;
            case "/insert":
                insert(request, response);
                break;
            case "/list":
                list(request, response);
                break;
            case "/delete":
                delete(request, response);
                break;
            case "/edit":
                showEdit(request, response);
                break;
            case "/update":
                update(request, response);
                break;
            default:
                // Default para listagem (Trabalho Prático 03)
                list(request, response);
                break;
        }
    }

    // Ações de Visualização (Forward)
    private void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Livro> livros = dao.getLista();
        request.setAttribute("livros", livros);
        request.getRequestDispatcher("/WEB-INF/views/lista-livros.jsp")
                .forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/formulario-livro.jsp")
                .forward(request, response);
    }

    private void showEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Captura ID (ex: /edit?id=1)
        Long id = Long.parseLong(request.getParameter("id"));
        // Livro livro = dao.buscaPorId(id); // Implementar no DAO
        // request.setAttribute("livro", livro);
        request.getRequestDispatcher("/WEB-INF/views/formulario-livro.jsp")
                .forward(request, response);
    }

    // Ações de Persistência (Redirect)
    private void insert(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Recuperar parâmetros (Aula 17)
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String categoria = request.getParameter("categoria");
        int ano = Integer.parseInt(request.getParameter("anoPublicacao"));

        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setCategoria(categoria);
        livro.setAnoPublicacao(ano);

        dao.adiciona(livro);

        // Redirecionar para evitar re-submissão (Trabalho Prático 03)
        response.sendRedirect("list");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        dao.remove(id);
        response.sendRedirect("list");
    }

    private void update(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Lógica de update similar ao insert
        response.sendRedirect("list");
    }
}
