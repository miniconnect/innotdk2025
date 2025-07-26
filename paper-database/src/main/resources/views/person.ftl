<#import "frame.ftl" as frame>
<@frame.frameHeader title="Person: ${person.firstname} ${person.lastname}" />
<h1>PERSON</h1>
<p>Name: ${person.firstname} ${person.lastname}</p>
<p>Birth year: ${person.birthYear}</p>
<@frame.frameFooter />
