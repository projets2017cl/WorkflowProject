<html><head><title>DbTableShow</title>
<#include "DbTableShowCss.css">
      <body>
          <form name="ListOfTables" action="show" method="POST">
  <table class="DbTableShow">

      <thead>

          <tr>
              <th><input type="checkbox" onClick="toggle(this);"/></th>
              <th>Ajouter</th>
              <th>Nom de la vue</th>
              <th>Type</th>
              <th>Remarques</th>
          </tr>
          
      </thead>
      <tbody>
      <#list ShowTables as showtable>
      <tr>
      <td><input type="checkbox" id="${showtable}" name="ALL" value="${showtable}"/></td><td></td><td>${showtable}</td><td>TABLE</td><td><input type="text"/></td>
      
      </tr>
       </#list>
      

     <script language="JavaScript"> 
      var selectedIds = [];
function toggle(source) {
    checkboxes = document.getElementsByName('ALL');
    for ( var i in checkboxes)
        checkboxes[i].checked = source.checked;
}
function addSelects() {

    var ids = document.getElementsByName('ALL');

    for ( var i = 0; i < ids.length; i++) {
        if (ids[i].checked == true) {
            selectedIds.push(ids[i].value);
        }
    }
}
      </script>
          
     </tbody>
 
       
     </table>  
               <input type="Submit" value="Envoyer"/>
     
      </form>         
   
   </body>
      
</html>

