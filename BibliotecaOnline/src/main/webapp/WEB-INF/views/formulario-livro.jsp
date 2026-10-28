<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Livro - Biblioteca</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>
<div class="container">
    <div class="form-container">
        <h1>
            <c:choose>
                <c:when test="${not empty livro}">Editar Livro</c:when>
                <c:otherwise>Cadastrar Novo Livro</c:otherwise>
            </c:choose>
        </h1>
        <p class="subtitle">Preencha os dados do livro abaixo</p>

        <form action="${not empty livro ? 'update' : 'insert'}" method="post">
            <c:if test="${not empty livro}">
                <input type="hidden" name="id" value="${livro.id}" />
            </c:if>

            <div class="form-group">
                <label for="titulo">Título *</label>
                <input type="text" id="titulo" name="titulo" value="${livro.titulo}" required placeholder="Digite o título do livro" />
            </div>

            <div class="form-group">
                <label for="autor">Autor *</label>
                <input type="text" id="autor" name="autor" value="${livro.autor}" required placeholder="Digite o nome do autor" />
            </div>

            <div class="form-group">
                <label for="categoria">Categoria *</label>
                <select id="categoria" name="categoria" required>
                    <option value="">Selecione uma categoria</option>
                    <option value="Economia" ${livro.categoria == 'Economia' ? 'selected' : ''}>Economia</option>
                    <option value="Empreendedorismo" ${livro.categoria == 'Empreendedorismo' ? 'selected' : ''}>Empreendedorismo</option>
                    <option value="Psicologia" ${livro.categoria == 'Psicologia' ? 'selected' : ''}>Psicologia</option>
                    <option value="Administracao" ${livro.categoria == 'Administracao' ? 'selected' : ''}>Administração</option>
                    <option value="Direito" ${livro.categoria == 'Direito' ? 'selected' : ''}>Direito</option>
                    <option value="Sociologia" ${livro.categoria == 'Sociologia' ? 'selected' : ''}>Sociologia</option>
                    <option value="Teologia" ${livro.categoria == 'Teologia' ? 'selected' : ''}>Teologia</option>
                </select>
            </div>

            <div class="form-group">
                <label for="anoPublicacao">Ano de Publicação</label>
                <input type="number" id="anoPublicacao" name="anoPublicacao" value="${livro.anoPublicacao}" placeholder="Ex: 2024" min="1000" max="2030" />
            </div>

            <div class="form-group">
                <label for="descricao">Descrição</label>
                <textarea id="descricao" name="descricao" placeholder="Digite uma breve descrição do livro (opcional)">${livro.descricao}</textarea>
            </div>

            <div class="form-actions">
                <input type="submit" value="💾 Gravar Livro" class="btn btn-primary" />
                <a href="list" class="btn btn-secondary">← Voltar para Lista</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>