<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Livros - Biblioteca</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<header class="topbar">
    <div class="topbar-inner">
        <a class="brand" href="list">
            <span class="brand-icon">📚</span> Biblioteca Online
        </a>
        <nav>
            <a href="list" class="btn btn-secondary btn-small">Início</a>
            <a href="new" class="btn btn-primary btn-small">+ Novo Livro</a>
        </nav>
    </div>
</header>

<div class="container">
    <h1>📚 Biblioteca Online</h1>
    <p class="subtitle">Seu acervo digital de conhecimento</p>

    <div class="categorias">
        <strong>Categorias disponíveis:</strong>
        <span class="chip">Economia</span>
        <span class="chip">Empreendedorismo</span>
        <span class="chip">Psicologia</span>
        <span class="chip">Administração</span>
        <span class="chip">Direito</span>
        <span class="chip">Sociologia</span>
        <span class="chip">Teologia</span>
    </div>

    <!-- Busca simples no navegador (sem precisar alterar o Servlet) -->
    <div class="search-bar">
        <input type="text" id="buscaLivros" placeholder="🔍 Buscar por título ou autor..." onkeyup="filtrarTabela()" />
    </div>

    <a href="new" class="btn btn-primary">+ Cadastrar Novo Livro</a>

    <div class="table-container">
        <c:choose>
            <c:when test="${empty livros}">
                <div class="empty-message">
                    <p>Nenhum livro cadastrado ainda.</p>
                    <p>Clique no botão acima para adicionar o primeiro livro!</p>
                </div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                    <tr>
                        <th>Título</th>
                        <th>Autor</th>
                        <th>Categoria</th>
                        <th>Ano</th>
                        <th>Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="livro" items="${livros}">
                        <tr>
                            <td><strong>${livro.titulo}</strong></td>
                            <td>${livro.autor}</td>
                            <td><span class="badge">${livro.categoria}</span></td>
                            <td>${livro.anoPublicacao}</td>
                            <td>
                                <div class="actions">
                                    <a href="edit?id=${livro.id}" class="btn btn-secondary btn-small">Editar</a>
                                    <a href="delete?id=${livro.id}" class="btn btn-danger btn-small" onclick="return confirm('Tem certeza que deseja excluir este livro?')">Excluir</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<footer class="footer">
    Biblioteca Online — Trabalho Prático 03 &middot; Java JSP + Apache Tomcat
</footer>

<script>
    // Filtro simples da tabela (client-side, não altera o Servlet)
    function filtrarTabela() {
        const termo = document.getElementById('buscaLivros').value.toLowerCase();
        const linhas = document.querySelectorAll('tbody tr');
        linhas.forEach(linha => {
            const texto = linha.innerText.toLowerCase();
            linha.style.display = texto.includes(termo) ? '' : 'none';
        });
    }
</script>
</body>
</html>