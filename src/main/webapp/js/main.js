var debug = false;
var $curentActivMenu;
var $workPanel;
var $txtCalculateSelect;
var $objectTitle;
var $menuCalculateSelect; 
var $btnAreaSelectText;
var currentCalculateID = -1; //set from cook
var $podborCriteria = null;
var $factValues = null;
var $scenarioTable = null;
var $evalTable = null;
var $hintAsyncUpdate;
var scenariosCheckCount = -1;
var scenariosCriteriaCheckCount = -1;
var $menu;
var isChangeForRaschet = true;

$(document).ready(function() {
	initComponents();
	//loadObjectList(object_list);
});


function initComponents() {
	$hintAsyncUpdate = $('#hintAsyncUpdate');
	$menu = $('#menu');
		
	$("#hintAsyncUpdate,#loading,#tinyCenterContent,#tinyModalContent,#newCalculation,#startCalculate,#expotExcel").hide();

	$('button#enterSubmit').click(function() {
		doEnterSubmit();
	});
	
	$('button#newCalculation').click(function() {
		isChangeForRaschet = true;
		doNewCalculation();
	});
	
	$('button#startCalculate').click(function() {
		doStartCalculate();
	});
	
	$('button#expotExcel').click(function() {
		doExportEcel();
	});
	
	$curentActivMenu = $('#ALL_REZULT').parents('li').addClass("active");

	$menu.click(function(event) {
		doMenuClick(event.target);
	});

	$workPanel = $('#workPanel');
	$txtCalculateSelect = $('#txtCalculateSelect');
	$objectTitle = $('#objectTitle');
	$menuCalculateSelect = $('#menuCalculateSelect'); 
	$btnAreaSelectText = $('#btnAreaSelectText');
	refreshMenu();
}

function doWGObjetClick(el) {
	clearWorkSpace(); 
	$txtCalculateSelect.text('Выбор расчета.');
	var $el=$(el);
	var id = $el.attr('objectid')
	$objectTitle.text($el.data('PARENT_NAME'));
	$("#newCalculation").show();
	callJSONData('getculclist.json', loadCalculateList, {ID: id});
}

function clearWorkSpace() {
	$txtCalculateSelect.text('');
	$objectTitle.text('');
	$menuCalculateSelect.empty(); 
	currentCalculateID = null;
	$("#startCalculate,#newCalculation,#expotExcel").hide();
	clearWorkPanel();
}

function clearWorkPanel() {
	$workPanel.empty();
	$podborCriteria = null;
	$factValues = null;
	scenariosCheckCount = -1;
	scenariosCriteriaCheckCount = -1;
	refreshMenu();
}

function loadCalculateList(data) {
	var $list = $menuCalculateSelect.empty(); 
	$txtCalculateSelect.html(' <span class="badge">' + data.length + '</span> Выбор расчета.');
	$
			.each(
					data,
					function(index, calc) {
							($('<li><a href="#" calculateid="'
									+ calc['ID'] + '">'
									+ decodeText(calc['NAME'])
									+ ' </a></li> ')).appendTo($list)
					});
	$('#menuCalculateSelect a[calculateid]').click(function(event) {
		doSelectCalculateClick(event.target);
	});
}

function doSelectCalculateClick(el) {
	var $el=$(el);
	$txtCalculateSelect.text('Расчет: ' + $el.text());
	$("#expotExcel").show();
	currentCalculateID = $el.attr('calculateid');
	callJSONData('getculccontext.json', prepareCalculation, {ID:currentCalculateID});
}

function prepareCalculation(data) {
	isChangeForRaschet = true;
	clearWorkPanel();

	var scenario = data['SCENARIOS'];
	var criteria = data['CRITERIAS'];
	var scenario_criteria = data['SCENARIOS_CRITERIA'];
	var scenario_group = data['SCENARIOS_GROUP'];

	if (scenario.length > 0) {
		showWait();
		setTimeout(function() {
			preparePodborCritetia(scenario, criteria, scenario_criteria);
			prepareFactValues(criteria);
			prepareScenarioValues(scenario, scenario_group);
			$('#SCENARY').click();
			hidelWait();
		}, 20);
	}
}

function prepareFactValues(criteria) {
	var sc = '<div class=""><table id="tableFactValues" class="table table-bordered table-condensed table-striped"> <thead> <tr> <th   rowspan="3" >' 
		+ ' № </th> <th  rowspan="3" >' 
		+ ' Обозначение </th> <th  rowspan="3" >' 
		+ ' Наименование </th> <th  colspan="3" >' 
		+ ' Предельно допустимые значения </th> <th  rowspan="3" >' 
		+ ' Фактическое значение </th> <th  rowspan="3" >' 
		+ ' Результат оценки<br>(φ фi) </th> </tr> <tr > <th rowspan="2"  >' 
		+ ' К1 </th> <th rowspan="2"  >' 
		+ ' К2 </th> <th rowspan="2"  >' 
		+ ' К3 </th> </tr> </thead>';
	sc += '<tbody>';

	for (c in criteria) {
		var crit = criteria[c];
		var sign = crit['SIGN'];
		// Пропускаем критерии далее "в"
		if (sign[0] > 'в'){
			continue;
		}
		
		var name = decodeText(crit['NAME']);
		var npp = crit['NPP'];
		var k1 = decodeText(crit['K1']);
		var k2 = decodeText(crit['K2']);
		var k3 = decodeText(crit['K3']);
		var actValue = decodeText(crit['ACTUAL_VALUE']);
		var fiValue = decodeText(crit['FI']);
		var id = crit['ID'];
		var critID = crit['CRITERIA_ID'];
		
		sc += '<tr id="critRow' + critID + '">';
 		sc += '<td class="text-center vertical-center">' 
			+ npp +'</td>';
 		
 		sc += '<td class="text-center vertical-center">' 
 			+ sign +'</td>';
 		
		sc += '<td class="small vertical-center" style="width:200px;">' 
			+ name +'</td>';

		sc += '<td k="1" criteriaID="'+id+'" class="small kvalue" style="margin: 0px;padding: 2px;width: 140px;">' 
			+ k1 +'</td>';
		
		sc += '<td k="2" criteriaID="'+id+'" class="small kvalue" style="margin: 0px;padding: 2px;width: 140px;">' 
			+ k2 +'</td>';
		
		sc += '<td k="3" criteriaID="'+id+'" class="small kvalue" style="margin: 0px;padding: 2px;width: 140px;">' 
			+ k3 +'</td>';
		
		sc += '<td criteriaID="'+id+'" class="small actualValue" style="margin: 0px;padding: 2px;width: 200px;">'
			+ actValue
			+'</td>';
		
		sc += '<td criteriaID="'+id+'" class="small fiValue" style="margin: 0px;padding: 2px;width: 100px;">'
			+ fiValue + '</td>';
		
		sc += '</tr>';
	}
	
	sc +=  '</tbody></table></div>';
	
	$factValues = $(sc);
	$factValues.find('th').addClass('text-center').css('vertical-align','middle');
	$workPanel.empty().append($factValues);
	
	
	$factValues.find('tr').each(function(index, el) {
		var $tr =  $(el);
		var maxHeight = 100;
		$factValues.find('td').each(function(index, el1) {
			var ch = el.clientHeight + 4;
			if (ch > maxHeight){
				maxHeight = ch;
			}
		});
		
		$tr.find('td.fiValue').each(function(index, el2) {
			var $el = $(el2);
			$el.html('<input dataTable="CALCULATION_CRITERIA" dataField="FI" dataID="'
					+ $el.attr('criteriaID') + '" class="dataSaveField inputFiValue" style="width: 100px;height: '
					+ maxHeight + 'px;padding: 0px;margin: 0px;border-style: none;text-align: center;" value="'
					+ $el.text() + '">')
		});

		$tr.find('td.actualValue').each(function(index, el2) {
			var $el = $(el2);
			$el.html('<textarea dataTable="CALCULATION_CRITERIA" dataField="ACTUAL_VALUE" dataID="'
					+ $el.attr('criteriaID') + '" class="dataSaveField" style="width: 200px;height: '
					+ maxHeight + 'px;padding: 0px;margin: 0px;border-style: none;">'
					+ $el.text() + '</textarea>')
		});
		
		$tr.find('td.kvalue').each(function(index, el2) {
			var $el = $(el2);
			var dataField = 'K' + $el.attr('k');
			$el.html('<textarea dataTable="CALCULATION_CRITERIA" dataField="'
					+ dataField + '" dataID="'
					+ $el.attr('criteriaID') + '" class="dataSaveField" style="width: 140px;height: '
					+ maxHeight+'px;padding: 0px;margin: 0px;border-style: none;">'
					+ $el.text() + '</textarea>')
		});		

	});

	refreshMenu();
	//setFixetHeadToTable($factValues.find('table'));
}

function prepareScenarioValues(scenario, scenario_group) {
	var listScenGroup = {};
	for (i in scenario_group){
		listScenGroup[scenario_group[i]['ID']] = scenario_group[i]['NAME'];
	}
	
	scenariosCheckCount = 0;
	
	var sc = '<div class=""><table id="tableFactValues" class="table table-bordered table-condensed table-striped"><thead><tr>' 
		+ '<th> &radic; </th>'
		+ '<th> №№ </th>'
		+ '<th> Описание </th>'
		+ '</tr> </thead>';
	sc += '<tbody>';
	var curentGrID = -1;

	for (c in scenario) {
		var cScenario = scenario[c];

		var groupID = cScenario['SCENARIOS_GROUP_ID'];
		if (groupID != curentGrID) {
			sc += '<tr><th colspan=3>' + listScenGroup[groupID] + '</th></tr>';
			curentGrID = groupID;
		}

		var name = decodeText(cScenario['NAME']);
		var npp = cScenario['NPP'];
		var id = cScenario['ID'];
		var check = cScenario['CHECKED'];
		sc += '<tr>';

		sc += '<td class="text-center vertical-center">' + '<input id="' + id
				+ '"';

		if (check) {
			sc += ' checked ';
			scenariosCheckCount++;
		}

		sc += '"  data-toggle="tooltip" data-trigger="hover" data-placement="left" type="checkbox" >'
				+ '</td>';

		sc += '<td class="vertical-center">' + npp + '</td>';

		sc += '<td class="small">' + name + '</td>';

		sc += '</tr>';
	}
	
	sc +=  '</tbody></table></div>';
	
	$scenarioTable = $(sc);
	$menu.find('li:has(a#SCENARY)').show();
	refreshMenu();
	$workPanel.empty().append($scenarioTable);
	//setFixetHeadToTable($scenarioTable.find('table'));
}

function preparePodborCritetia(scenario, criteria, scenario_criteria) {
	
	scenariosCriteriaCheckCount = 0;
	
	var sc = '<div class=""><table id="tablePodborCriteria" class="table table-bordered table-condensed table-striped table-hover">';
	   
	sc += '<thead> <tr> <th class="small" style="width: 100px;" >Обозначение критерия</th> <th  style="width:300px;">Название критерия</th>';
	
	for (i in scenario){
		var scen = scenario[i];
		var scenID = scen['ID'];
		sc += '<th class="small scenCol' + scenID + '" title="' 
			+ decodeText(scen['NAME']) + '" style="width: 20px;">' 
			+ scen['NPP'] +'<BR><span id="scenarioBadge' 
			+ scenID + '" class="badge"></span></th>';
	}
	
	sc += '</tr></thead>';
	sc += '<tbody>'
	
	for (c in criteria){
		var crit = criteria[c];
		var sign = crit['SIGN'];
		var critID = crit['CRITERIA_ID'];
		
		// Пропускаем критерии далее "в"
		if (sign[0] > 'в'){
			continue;
		}
		
		sc += '<tr critID="' + critID 
		+ '"><th class="text-center vertical-center" style="vertical-align: middle;">' 
			+ sign +'</th>';
		sc += '<th class="small">' 
			+ crit['NAME'] +'</th>';

		for (i in scenario){
			var scen = scenario[i];
			var scenID = scen['ID'];
			
			var scID = scenID + '-' + critID;
			var checked = '';
		
			if(!scen['count']){
				scen['count'] = 0;
			}
			
			if (scenario_criteria[scID]){
				checked = 'checked';
				scen['count'] = scen['count'] +1;
			}
			
			sc += '<td class="text-center vertical-center scenCol' + scenID 
				+ '" style="vertical-align: middle;">'
				+'<input  data-toggle="tooltip" data-trigger="hover" data-placement="left" type="checkbox" '
				+'id="'	+ scID + '" ' 
				+ checked + ' title="' 
				+ 'Сценарий: (' + scen['NPP'] + ') ' + decodeText(scen['NAME']) 
				+ '\n\nКритерий: (' + sign  + ') '+ decodeText(crit['NAME'])
				+ '"></td>';
		}
		sc += '</tr>';
	}
	
	sc +=  '</tbody></table></div>';
	
	$podborCriteria = $(sc);

	for (i in scenario){
		var scen = scenario[i];
		var scenID = scen['ID'];
		
		var count = scen['count'];
		$podborCriteria.find('span#scenarioBadge' + scenID).text(count);
		
		if(!scen['CHECKED']) {
			$podborCriteria.find('.scenCol' + scenID).hide(); 
		} else {
			scenariosCriteriaCheckCount += parseInt(count);
		}
	}
	//$podborCriteria.find('input').tooltip();
	refreshMenu();
	//$workPanel.empty().append($podborCriteria);
	//setFixetHeadToTable($podborCriteria.find('table#tablePodborCriteria'));

}

function showFactValues() {
	if ($factValues) {
		showWait();
		setTimeout(function() {
			var showRows = '#XXXX';
			var hideRows = '#XXXX';

			$podborCriteria.find('tr').each(function(index, el) {
				var $el = $(el);
				if ($el.attr('critID')) {
					var critID = ',tr#critRow' + $el.attr('critID');
					var $visTd = $el.find('td').filter(function( index ) {
					    return this.style.display != 'none';
					  });
					
					if ($visTd.find('input:checked').length) {
						showRows += critID;
					} else {
						hideRows += critID;
					}
				}
			});

			$workPanel.empty().append($factValues);
			$factValues.find(showRows).show();
			$factValues.find(hideRows).hide();

			$factValues.find('.dataSaveField').change(
					function(event) {
						doFieldValueChange(event.target);
					});

			$factValues.find('.inputFiValue').change(
					function(event) {
						checkReadyFactValues();
					});
			// $workPanel.find('table').floatThead('reflow');
			
			checkReadyFactValues();
			hidelWait();

		}, 20);
	}
}

function checkReadyFactValues() {
		setTimeout(function() {
			var isReady = true;
			var isFind = false;
			$factValues.find('tr').filter(function( index ) {
			    return this.style.display != 'none';
			  }).find('.inputFiValue').each(function(index, el) {
				  isFind = true;
				  var $el = $(el);
				  if (!$.trim($el.val())){
					  isReady = false;
					  return false;
				  }
			});
			if (isFind && isReady) {
				$menu.find('li:has(#EVAL_TABLE)').show();
			} else {
				$menu.find('li:has(a#EVAL_TABLE)').hide();
			}
		}, 20);

}

function showScenario() {
	if ($scenarioTable) {
		showWait();
		setTimeout(function() {
			$workPanel.empty().append($scenarioTable);
			// setFixetHeadToTable();
			$scenarioTable.find('input').click(function(event) {
				doScenarioCheckClick(event.target);
			});
			$workPanel.find('table').floatThead('reflow')
			hidelWait();
		}, 20);
	}
}

function showCriteria() {
	if ($podborCriteria) {
		showWait();
		setTimeout(function() {
			var $clone = $podborCriteria.clone();
			$workPanel.empty().append($clone);
			
			//$workPanel.find('table#tablePodborCriteria').floatThead('destroy');
			$clone.find('table#tablePodborCriteria').floatThead();
			$clone.find('input').click(function(event) {
				doPodborCheckClick(event.target, $clone);
			});
			//setFixetHeadToTable();
			//setFixetHeadToTable($podborCriteria.find('table'));

			hidelWait();
		},20)

	}
}

function doFieldValueChange(el) {
	var $el = $(el);
	var dataID = $el.attr('dataID');
	var dataTable = $el.attr('dataTable');
	var dataField = $el.attr('dataField');
	var value = $el.val();
	
	$hintAsyncUpdate.show();
	
	callJSONData('updatefield.json', hidelWait, {
		DATA_TABLE : dataTable,
		DATA_FIELD : dataField,
		DATA_VALUE : value,
		DATA_ID : dataID
	});
	
	refreshMenu();
}

function doPodborCheckClick(el, $parent) {
	var $elCloned = $(el);
	var id = $elCloned.attr('id');
	var sc = id.split('-');
	var scenID = sc[0];
	var critID = sc[1];
	var checked = (el.checked) ? 1 : 0;
	var $el = $podborCriteria.find('input#' + id);
	if (el.checked) {
		$el.attr('checked', checked);
	} else {
		$el.removeAttr('checked');
	}
	var $scCount = $podborCriteria.find('span#scenarioBadge' + scenID);
	var $scCountParent = $parent.find('span#scenarioBadge' + scenID);
	
	var delta = ((checked == 0) ? -1 : 1);
	var count = parseInt($scCount.text()) + delta;
	
	scenariosCriteriaCheckCount = scenariosCriteriaCheckCount + delta;
	
	$scCount.text(count);
	$scCountParent.text(count);
		
	$hintAsyncUpdate.show();
	
	callJSONData('updatecritset.json', hidelWait, {
		CALCULATION_ID : currentCalculateID,
		SCENARIOS_ID : scenID,
		CRITERIA_ID : critID,
		CHECK_VALUE : checked,
	});
	
	refreshMenu();
}

function doScenarioCheckClick(el) {
	$hintAsyncUpdate.show();
	
	var $el = $(el);
	var scenID = $el.attr('id');
	var checked = (el.checked) ? 1 : 0;
	var delta = (checked == 0) ? -1 : 1;

	scenariosCheckCount = scenariosCheckCount + delta;

	scenariosCriteriaCheckCount += delta
			* parseInt($podborCriteria.find('span#scenarioBadge' + scenID)
					.text());
	
	callJSONData('updatescenset.json', hidelWait, {
		CALCULATION_ID : currentCalculateID,
		SCENARIOS_ID : scenID,
	    CHECK_VALUE : checked,
	});
	
	if(checked) {
		$podborCriteria.find('.scenCol' + scenID).show(); 
	} else {
		$podborCriteria.find('.scenCol' + scenID).hide(); 
	}
	refreshMenu();
}

function decodeText(text){
	try {
		return decodeURIComponent(text);
	} catch (e) {
		if (debug) {
			console.error('Decode text:"'+text+'", error: ' + e);
		}
	} 
		
	return text;
}
 
function doMenuAreaClick(el) {
	clearWorkSpace();
	var $el=$(el);
	var $elp=$(el.parentElement);
	var data = $elp.data('UZELS');
	var controlName = $elp.data('CONTROL_NAME');
	var areaName = $elp.data('AREA_NAME');
	$btnAreaSelectText.text($el.text()).prop('title', $el.text());
	setObjectListMenu(data, controlName, areaName);
}

function doMenuClick(el) {
	var $parentElement = $(el.parentElement);
	
	if ($parentElement.hasClass("active")){
		return;
	}
	
	$curentActivMenu.removeClass("active");
	
	$curentActivMenu = $parentElement.addClass("active");
	
	switch (el.id) {
	case 'FACT_VALUE':
		showFactValues();
		break;
	case 'SEL_CRIT':
		showCriteria();
		break;
	case 'SCENARY':
		showScenario();
		break;
	case 'EVAL_TABLE':
		showEvalTable();
		break;
	case 'ALL_REZULT':
		loadPageToWork('pages/all_result' + currentCalculateID + '.htm', function() {
			fixWorkPanelAfterLoadPage();
			hidelWait();
		});
		break;
	case 'BAD_REZULT':
		loadPageToWork('pages/bad_result' + currentCalculateID + '.htm', function() {
			fixWorkPanelAfterLoadPage();
			hidelWait();
		});
		break;
	}
}

function refreshMenu() {
	isChangeForRaschet = true;
	if (scenariosCheckCount == -1) {
		$menu.find('li').hide();
	} else if (scenariosCheckCount) {
		$menu.find('li:has(a#SEL_CRIT)').show();
		if (scenariosCriteriaCheckCount) {
			$menu.find('li:has(#FACT_VALUE)').show();
			checkReadyFactValues();
		} else {
			$menu.find('li:has(a#FACT_VALUE),li:has(a#EVAL_TABLE)').hide();
		}
	} else {
		$menu.find('li:has(a#SEL_CRIT),li:has(a#FACT_VALUE),li:has(a#EVAL_TABLE)').hide();
	}

	// SCENARY EVAL_TABLE SEL_CRIT ALL_REZULT BAD_REZULT

}

function showEvalTable(isRashchet) {
	showWait();
	if (isChangeForRaschet || isRashchet) {
		isChangeForRaschet = false;
		callJSONData('getevaltable.json', prepareEvalTable, {
			ID : currentCalculateID,
			RASCHET : isRashchet
		});
	} else {
		setTimeout(function() {
			$workPanel.empty().append($evalTable);
			$evalTable.find('.dataSaveField').change(function(event) {
				doFieldValueChange(event.target);
			});
			$('#startCalculate').off("click").show().click(function() {
				showEvalTable('1')
			});
			
			
			hidelWait();
		}, 20);
	}
}

function prepareEvalTable(evalTableData) {
	
	
	var sc = '<div class="">'
		+ 'Результаты оценки и ранжирования критериев безопасности групп А Б и В, а так же результаты оценки критериев групп Г, Д, Е для сценариев аварии 1, 2 и 3 групп'
		+ '<table id="tableEvalTable" class="table table-bordered table-condensed table-striped"> <thead>'
		+ ' <tr> '
		+ '<th rowspan="3" class="rotate-90 small"> №№ сценариев </th> '
		+ '<th rowspan="3" class="small"> Обозначение критерия безопасности </th> '
		+ '<th rowspan="3" > Наименование критерия безопасности групп А, Б и В </th> '
		+ '<th rowspan="3" class="small"> Результат оценки критерия (φфi) </th> '
		+ '<th rowspan="3" > Ранг </th> '
		+ '<th rowspan="3" class="small"> Коэффициент важности (значимости) Кзнi </th> '
		+ '<th rowspan="3" class="small"> Уточнённый результат оценки критерия (φi) </th> '
		+ '<th colspan="5" class="small"> Критерии безопасности групп Г, Д и Е </th>' 
		+ '</tr><tr>' 
		+ '<th rowspan="2"> Г </th>' 
		+ '<th rowspan="2"> Д </th>' 
		+ '<th rowspan="2"> Е1 </th>' 
		+ '<th rowspan="2"> Е2 </th>' 
		+ '<th rowspan="2"> Е3 </th>' 
		+ '</tr></thead>';
	sc += '<tbody>';

	var curNPP = '';
	
	for (c in evalTableData) {
		var etab = evalTableData[c];
		var sign = etab['SIGN'];
		var name = decodeText(etab['NAME']);
		var npp = etab['SCENARIOS_NPP'];
		var id = etab['ID'];
		var fi = etab['FI'];
		var rang = etab['RANG'];
		var kzni = etab['KZNI'];
		var utochfi = etab['UTCHFI'];
		var g = etab['G'];
		var d = etab['D'];
		var e1 = etab['E1'];
		var e2 = etab['E2'];
		var e3 = etab['E3'];
		var isNewNPP = (npp != curNPP);
		
		sc += '<tr'+((isNewNPP)?' style="border-top-style: double;"':'')+'>';
 		sc += '<td class="text-center vertical-center">'
 		
		if (isNewNPP){
			sc += npp;
			curNPP = npp;
		}
			
		sc += '</td>';
 		
 		sc += '<td class="text-center vertical-center">' 
 			+ sign +'</td>';
 		
		sc += '<td class="small vertical-center" style="width:400px;">' 
			+ name +'</td>';

 		sc += '<td class="text-center vertical-center">' 
 			+ fi +'</td>';

		sc += '<td class="text-center vertical-center" style="margin: 0px;padding: 0px;width: 30px;vertical-align: middle;">' 
			+ '<input dataTable="CALCULATION_TABLE" dataField="RANG" dataID="'
			+ id 
			+ '" class="dataSaveField" style="padding: 0px;margin: 0px;border-style: none;text-align: center;height: 100%;width:50px;" value="'
			+ rang + '"></td>';
		
 		sc += '<td class="text-center vertical-center">' 
 			+ kzni +'</td>';
		
 		sc += '<td class="text-center vertical-center">' 
 			+ utochfi +'</td>';
 		
 		sc += '<td class="text-center vertical-center">' 
 			+  normalizeFloat(g) +'</td>';
 		
 		sc += '<td class="text-center vertical-center">' 
 			+  normalizeFloat(d) +'</td>';
 		
 		sc += '<td class="text-center vertical-center">' 
 			+  normalizeFloat(e1) +'</td>';
 		
 		sc += '<td class="text-center vertical-center">' 
 			+  normalizeFloat(e2) +'</td>';
 		
 		sc += '<td class="text-center vertical-center">' 
 			+  normalizeFloat(e3) +'</td>';
 		
		sc += '</tr>';
	}
	
	sc +=  '</tbody></table></div>';
	
	$evalTable = $(sc);
	
	isChangeForRaschet = false;
	showEvalTable()
}
function normalizeFloat(value) {
	
	if (value == '-1'){
		return '';
	}
	return value;
}

function fixWorkPanelAfterLoadPage() {
	setFixetHeadToTable();
	
	  $workPanel.find('td,th').css({'height':'','width':'', 'border':'1px solid black'}).prop('width','');
	  $workPanel.find('tr,p').css({'height':'','width':'', 'border':'none'}).prop('width','');
	  
	  $workPanel.find('td, th').each(function( index, el ) {
		  var $his=$( el );
		var text=$his.text();
		$his.text(text);
	  });

	  
	  $workPanel.find('tr').filter(function( index ) {
		  var text=$( this ).text();
		  return text.indexOf("РАБОТОСПОСОБНОЕ") != -1 || text.indexOf("ИСПРАВНОЕ") != -1;
	  }).css('background-color','aquamarine');
	  

	  $workPanel.find('tr').filter(function( index ) {
		  return $( this ).text().indexOf("ОГРАНИЧЕННО") != -1;
	  }).css('background-color','antiquewhite');
	  
	  $workPanel.find('tr').filter(function( index ) {
		  return $( this ).text().indexOf("ПРЕДАВАРИЙНОЕ") != -1;
	  }).css('background-color','coral');

}

function setFixetHeadToTable($table) {
	if (!$table){
		$table = $workPanel.find('table');
	}	
	//$table.floatThead('destroy');
	$table.floatThead();
	
	//$table.floatThead('reflow');
}

function loadPageToWork(url, func) {
	showWait();
	if (func) {
		$workPanel.load(url, func);
	} else {
		$workPanel.load(url, function() {
			hidelWait();
		});
	}
}

function doEnterSubmit() {
	$("#enterForm").hide();
	$("#mainForm").show();
	showWait();
	loadPageToWork('pages/all_result.htm');
	callJSONData('getarea.json', loadObjectList, null);
}

function doExportEcel() {
	location.href = 'exportexcel.do?ID=' + currentCalculateID;
}

function loadObjectList(data) {
	var $list = $('#menuAreaSelect').empty(); 
	$
			.each(
					data,
					function(index, control) {
						var controlName=decodeText(control['NAME']);
						
						$list.append($('<li class="dropdown-header">'
								+ controlName + '</li> '));

						$.each(control['AREAS'], function(index, area) {
							var areaName=decodeText(area['NAME']);
							
							($('<li class="marginleft1em menuAreaUzel"><a href="#" id="area'
									+ area['ID'] + '">'
									+ areaName
									+ ' </a></li> ')).appendTo($list)
									.data('UZELS', area['UZELS'])
									.data('CONTROL_NAME', controlName)
									.data('AREA_NAME', areaName)
									.prop('search', areaName.toUpperCase());
						});
						$list.append($('<li class="divider"></li>'));
					});
	$('#menuAreaSelect li.menuAreaUzel').click(function(event) {
		doMenuAreaClick(event.target);
	});
}

function setObjectListMenu(data, controlName, areaName) {
	var $list = $('#objectList').empty();
	
	$
	.each(
			data,
			function(index, uzel) {
				var uzelName=decodeText(uzel['NAME']);
				var listObject = uzel['OBJECTS'];
				var uzelID = uzel['ID'];

				$list.append($('<div class="group-control"  data-toggle="collapse" data-target="#uzel'
						+ uzelID
						+ '"> <span class="caret"></span>  <span class="badge">' + listObject.length + '</span> '
						+ uzelName + '</div> '));
				
				var $oList = ($('<div id="uzel' + uzelID
				+ '" class="collapse group-items"></div> '))
				.appendTo($list);
			
				$.each(listObject, function(index, obj) {
					var $div = $('<div></div>').appendTo($oList);
					var objectName=decodeText(obj['NAME']);
					var objectTypeName=decodeText(obj['OBJECT_TYPE_NAME']);

					$('<span class="marginleft1em objectItem" objectID="'
							+ obj['ID'] + '" title="' + objectTypeName + '">'
							+ objectName
							+ ' </span>')
							.data('PARENT_NAME', controlName 
									+ ((controlName != areaName)? ' / ' + areaName:'')
									+ ((uzelName != areaName)? ' / ' + uzelName:'')
									+ ' / ' + objectName
							)
							.appendTo($div);
				});
			});
	$('#objectList span[objectid]').click(function(event) {
		doWGObjetClick(event.target);
	});
}

function showWait() {
	$("#loading,div#tinyCenterContent").css("z-index", "5000").show();
}

function hidelWait() {
	$hintAsyncUpdate.hide();
	$("#loading, div#tinyCenterContent").hide();
}

function doFilterTable() {
	var $filterField = $('#menuAreaSelectFilter');

	//var $tr = $('#menuAreaSelect').find('li:not(:has(input),.divider)');
	var $tr = $('#menuAreaSelect').find('li[search]');
	
	$tr.prop('title', '"' +$tr.text() + '\ncsc"' )
	var text = $.trim($filterField.val());

	if (text == '')
		$tr.show();
	else
		$tr.hide().filter('[search*="' + text + '"]').show();

}


