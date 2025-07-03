
function err()
{ if (!isInCycle) alert('Некорректные данные');
  output.document.close();
  isOK = false;  // это в случае ошибки используем в цикле вывода свойств объекта
  return(false);
};

//window.onerror  = err;
//document.onerror  = err;

isInCycle = false;    // переменная следит, находимся ли в цикле
                      // (для возможного пропуска вызывающих ошибку свойств
isOK = true;          // не было ли ошибки


function showProperties(testObject)
{
	if (testObject == null) {
		alert("Object is null!")
		return;
	}

 var outputDocument ='';
 var objectName = testObject.name;
   for ( property in testObject )
   {
      isInCycle = true;

      if ( isNaN(property) )
      { s1 = '.';   s2 = '' }
      else
      { s1 = '[';   s2 = ']' };

      fullPropertyName = objectName + s1 + property + s2;

      if (( typeof(testObject[property])==='object' ) && ( testObject[property]!==null ))
      {
         s1 = '<a href="javascript: parent.document.f.input_objectName.value=\'' + fullPropertyName + '\'; void(0)" onclick="showProperties()">';
         s2 = '</a>';
      }
      else { s1 = ''; s2 = '' };

      try {
			propertyValue = testObject[property];
		} catch (e) {
			propertyValue += ' ERROR typeof=' + e;
		}


      if (!isOK)   // если получение значения очередного свойства вызвало ошибку
      {
        outputDocument += 'Попытка получения значения свойства - '+fullPropertyName+' вызвала ошибку \n';
        isOK = true;
        continue;
      };

      try {
    	  if ( typeof(testObject[property])=='function' ||
           propertyValue==null ||
           propertyValue==''   ||
           property == 'innerHTML' ||
           property == 'innerText' ||
           property == 'outerHTML' ||
           property == 'outerText'
          	)
    		  continue;
      }catch (e) {
    	  property += ' ERROR typeof=' + e;
	}

      outputDocument +=  fullPropertyName + ' = ' + propertyValue  +'\n';
   };
   //alert(outputDocument)

   var w = window.open();
   w.document.write('<pre>' + outputDocument + '</pre>');
}
function ShowColection(w){
 var s='';
  for(var i=0;  i<w.length;i++){
   s+=w[i].tagName + '=' + w[i].id + ','+w[i].name +'; ';
                   }
alert(s)
}
