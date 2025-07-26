<#macro pager total current urlTemplate>

<#assign around = 3>

<#assign toDisplay = []>
<#list 0..(total - 1) as i>
<#if (i < around || i >= total - around || (i >= current - around && i <= current + around))>
<#assign toDisplay += [i]>
</#if>
</#list>

<#if (current > 0)>
<a href="${urlTemplate?replace("{}", current?c)}">« Prev</a>
</#if>

<#assign last = -1>
<#list toDisplay as i>
<#if (i - last > 1)>
...
</#if>
<#if (i == current)>
<b>${i + 1}</b>
<#else>
<a href="${urlTemplate?replace("{}", (i + 1)?c)}">${i + 1}</a>
</#if>
<#assign last = i>
</#list>

<#if (current < total - 1)>
<a href="${urlTemplate?replace("{}", (current + 2)?c)}">Next »</a>
</#if>

</#macro>
