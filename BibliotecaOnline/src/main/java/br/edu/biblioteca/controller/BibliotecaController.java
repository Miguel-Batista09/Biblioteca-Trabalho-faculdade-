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

// Mapeamento explícito das ações (Trabalho Prático 03).
// IMPORTANTE: este servlet NÃO pode ser mapeado em "/", pois isso faria o
// Tomcat entregar TODAS as requisições a ele — inclusive /css/estilos.css,
// que é um arquivo estático. Esse era o motivo de o CSS "não ser reconhecido".
// Agora apenas as ações abaixo passam pelo servlet; arquivos estáticos
// (css, js, imagens) são servidos pelo "default servlet" nativo do Tomcat.
@WebServlet(name = "BibliotecaController", urlPatterns = {
        "/list", "/new", "/form", "/insert", "/edit", "/update", "/delete"
})
public class BibliotecaController extends HttpServlet {

    private transient LivroDao dao;

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

        // Garante UTF-8 nas respostas (acentos corretos nos JSPs)
        response.setCharacterEncoding("UTF-8");

        String path = request.getPathInfo(); // ex.: "/list", "/new"...
        if (path == null || path.isEmpty() || "/".equals(path)) {
            path = request.getServletPath();
        }
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
                // Ação desconhecida: volta para a listagem (Trabalho Prático 03)
                list(request, response);
                break;
        }
    }

    // Ações de Visualização (Forward)
    private void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Filtro por categoria (botões de categoria da página): /list?categoria=Economia
        String categoria = request.getParameter("categoria");
        List<Livro> livros;
        if (categoria == null || categoria.isBlank()) {
            livros = dao.getLista();
        } else {
            livros = dao.getListaPorCategoria(categoria);
        }
        request.setAttribute("categoriaAtual", categoria);
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
        Livro livro = dao.buscaPorId(id);
        request.setAttribute("livro", livro);
        request.getRequestDispatcher("/WEB-INF/views/formulario-livro.jsp")
                .forward(request, response);
    }

    // Ações de Persistência (Redirect)
    private void insert(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Recupera parâmetros com UTF-8 (acentos no título/autor)
        request.setCharacterEncoding("UTF-8");
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String categoria = request.getParameter("categoria");
        String anoTexto = request.getParameter("anoPublicacao");
        int ano = (anoTexto == null || anoTexto.isBlank()) ? 0 : Integer.parseInt(anoTexto);

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
        request.setCharacterEncoding("UTF-8");
        Long id = Long.parseLong(request.getParameter("id"));
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String categoria = request.getParameter("categoria");
        String anoTexto = request.getParameter("anoPublicacao");
        int ano = (anoTexto == null || anoTexto.isBlank()) ? 0 : Integer.parseInt(anoTexto);

        Livro livro = new Livro();
        livro.setId(id);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setCategoria(categoria);
        livro.setAnoPublicacao(ano);

        dao.altera(livro);
        response.sendRedirect("list");
    }
}
