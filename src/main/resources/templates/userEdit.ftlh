<#--  -->
<#import "parts/common.ftlh" as c>

<@c.page>
    User editor
<#-- создаем форму, которая нас будет отправлять на "/user" методом "post" -->
    <form action="/user" method="post">
        <#-- какие-то данные -->
        <#-- отображаем данные, которые мы хотим отредактировать
        1. _csrf.token - post форма без него не будет принята сервером
        2. и сразу кладем сюда идентификатор пользователя (user.id)
        3. добавим сюда поле для редактирования пользователя и сразу передадим сюда значение, которое уже имеем
        4. отображаем список ролей-->
        <input type="text" name="username" value="${user.username}">
        <#-- делаем итерацию по списку ролей -->
        <#list roles as role>
        <#-- каждую роль будем выводить на отдельной строке -->
            <div>
                <#-- здесь выводим название роли - role
                так же выведем пометку активна/не активна данная роль
                user.roles?seq_contains(role) - возвращает булево значение
                ?string - преобразовываем в строку
                первое значение "checked" - что означает, что данный checkbox выбран
                либо - ничего... -->
                <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>
        </#list>
        <#-- для того, чтобы эти поля не отображались в виде текста помечаем их hidden -->
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit">Save</button>
    </form>
</@c.page>