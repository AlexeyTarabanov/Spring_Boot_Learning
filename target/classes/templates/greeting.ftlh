<#-- для использования шаблона - нужно его экспортировать,
и можно назначить ему какой-то alias (псевдоним) -->
<#import "parts/common.ftlh" as c>
<#-- макросы позволяют описывать блоки кода и потом их переиспользовать -->
<#-- при использовании макроса мы используем @ -->
<@c.page>
<div>Hello, user</div>
<a href="/main">Main page</a>
</@c.page>