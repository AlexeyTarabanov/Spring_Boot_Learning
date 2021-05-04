<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div>
        <@l.logout />
        <#-- для того, чтобы каждый раз не вводить вручную страницу пользователей
        добавим ее -->
        <span><a href="user">User List></a></span>
    </div>
    <div>
        <form method="post">
            <input type="text" name="text" placeholder="Введите сообщение"/>
            <input type="text" name="tag" placeholder="Тэг">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Добавить</button>
        </form>
    </div>
    <div>Список сообщений</div>
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter}">
        <button type="submit">Найти</button>
    </form>
<#-- перебираем коллекцию -->
    <#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
        </div>
    <#-- в слеучае, если коллекция пустая, выводим сообщение "No message" -->
    <#else>
        No message
    </#list>
</@c.page>