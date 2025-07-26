<#import "frame.ftl" as frame>
<#import "pagination.ftl" as pagination>

<@frame.frameHeader title="Persons" />
<h1>PERSONS</h1>

<#if persons?has_content>
<ul>
  <#list persons as person>
  <li>
    <a href="/persons/${person.id?c}">${person.firstname} ${person.lastname}</a>
  </li>
  </#list>
</ul>
<@pagination.pager total=totalPages current=page urlTemplate="/persons?page={}" />
<#else>
<p>Nothing found.</p>
</#if>

<@frame.frameFooter />
