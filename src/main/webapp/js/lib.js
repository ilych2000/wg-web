//library
var cook = getcookie();
var dictionary = 'dictionary.xhtm';
var DecFameWidth = 5;
var CheckedButtonFontWeight   =  'bold';
var UnCheckedButtonFontWeight =  'normal';
var CheckedButtonFontColor  = '#ff0000';
//var TabButtonStyle = 'padding:0;margin:-1;margin-top:2;';
var StrTitleArray = '';
var IDTitleArray = '';
var ColTabButtons = 0;
var MaxFrameHeight = 300;
var MarginFrameHeight = 5;
var MarginFrameHeightNoMax = 20;
var MaxFrameRows = 15;
var HeightOfRow = 20;
var HeightOfFilterTextBox = 17;
var HeightOfRowQuery = 21;
var pnp = ParentNameOfPage();
var ClassForTitles='ClassForTitles';
var CurentCardURL='';
var CurentReportURL='';
var envName = 'NONAME';
var windowHelp;
var openerStack = [];
var isProcessStop = true;

var isMSIE = isBrowser('MSIE');
var isMSIE6 = isMSIE && !isBrowser('MSIE 7') && !isBrowser('MSIE 8') && !isBrowser('MSIE 9') && !isBrowser('MSIE 10');

function isBrowser(s)
{
	return navigator.userAgent.indexOf(s)!=-1;
}

function isFulScreen()
{
	if (isBrowser('MSIE'))
		return document.documentElement.offsetHeight+30 >= window.screen.height;

	if (isBrowser('Firefox') && window.fullScreen)
		return window.fullScreen;

	return window.innerHeight+30 >= window.screen.height;
}

function ResizeToFullScreen()
{
    moveTo (0,0);
    resizeTo(screen.availWidth,screen.availHeight);
}

function setDicValue(dicTableNO,dicFieldNO,fieldNO,textID,valueSetID)
{
    var ret=[];
    var textItem = document.getElementById(textID);
    var hideItem = document.getElementById(valueSetID);
//    alert(dicTableNO + ',' + dicFieldNO + ',' +fieldNO);

    ret = _setDicValue(dicTableNO,dicFieldNO,fieldNO,textItem.value);
    if (ret)
    {
      textItem.value = ret['text'];
      hideItem.value = ret['cod'];
      textItem.title = textItem.value + ' (' + hideItem.value + ')';
    }
}

function setDicValueEdited(dicTableNO,dicFieldNO,fieldNO,textID,valueSetID)
{
    var ret=[];
    var textItem = document.getElementById(textID);
    var hideItem = document.getElementById(valueSetID);

//    alert(dicTableNO + ',' + dicFieldNO + ',' +fieldNO);
    ret = _setDicValue(dicTableNO,dicFieldNO,fieldNO,'');
    if (ret)
    {
      textItem.value = ret['text'];
      hideItem.value = ret['cod'];
    }
}

function _setDicValue(dicTableNO,dicFieldNO,fieldNO,textItem)
{
    var ret=[];
    var filterByName=textItem;

    while (filterByName!=undefined)
    {
    ret = window.showModalDialog(
            dictionary+'?tableNO='+dicTableNO+'&dicFieldNO='+dicFieldNO+'&fieldNO='+fieldNO+
            '&NAME='+encodeURIComponent(filterByName),null,
            'resizable:yes;scroll:no;status:no;dialogWidth:407px;dialogHeight:348px;center:yes'
            );
/*
    ret = window.open(
            dictionary+'?tableNO='+dicTableNO+'&dicFieldNO='+dicFieldNO+'&fieldNO='+fieldNO+
            '&NAME='+filterByName,null,
            'scroll:no;status:no;dialogWidth:400px;dialogHeight:315px;center:yes'
            );
*/

      if (ret) filterByName = ret['filterByName'];
        else break;
    }
    return ret;
}


function ReturnDicValue(cod,text)
{
    var ret = [];
    ret['cod']= cod;
    ret['text']= text;

    window.returnValue=ret;
    window.close();
}

function show_content_frames(elframes,url)
{
    if (elframes=="") {return; }
    var i, j;
    var arframes = new Array();
    arframes = elframes.split(',');
    var frmurl = '';
    var count = parent.frames.length;
    var countparam = arframes.length;

    for(i = 0; i < count; i++)
{
        var frameno = parent.frames[i].name;

        for(j = 0; j < countparam; j++)
    {
            var paramno = arframes[j];

            if (paramno == frameno)
            {
                if (url.indexOf("javascript",0)==-1)
                {
                    var frameurl = new Array(3);
                    frameurl[0] = url + "";
                    frameurl[1] = "&FRAMENO=";
                    frameurl[2] = frameno + "";
                    frmurl = frameurl[0] + frameurl[1] + frameurl[2];

                    var actions = frmurl.split('?');
                    var actStr = actions[0];
                    var queryStr = actions[1];
                    var outStr =
                    '<STYLE>body {background: ButtonFace;}</STYLE>' +
                    '<form id=curKbForm action="' + actStr + '" method="POST">' +
                    '<input type="hidden" name="queryString" value="' + queryStr + '"/>' +
                    '</form>';

                    parent.frames[i].document.write(outStr);
                    parent.frames[i].document.getElementById('curKbForm').submit();
                }
                else
                {
                    frmurl = url;
                    parent.frames[i].location.href = frmurl;
                }
               break;
            }

        }
   	}
}

function ViewSignal(s1, s2) {
	var signal = parent.document.getElementById('signal');
	if (signal && s2 == 'T') {
		var zpt = ", ";
		if (signal.style.display != "block") {
			signal.style.display = "block";
			zpt = "";
		}
		if (signal.innerText.indexOf(s1) == -1)
			signal.innerText = signal.innerText.trim() + zpt + s1;
	}
}

function SetCurentCardURL(s1) {
	CurentCardURL = s1;
}

function SetCurentReportURL(s1) {
	CurentReportURL = s1;
}

function GOGO(s1, s2, s3, s4, s5) {
	DoProcessClose();
	if (s5 == '' || s5 != '' && s5.lastIndexOf("=") + 1 == s5.length
			|| s5 != CurentCardURL) {
		var kolf = 0;
		if (s1 != '')
			kolf++;
		if (s3 != '')
			kolf += s3.split(',').length;
		if (kolf > 0)
			DoProcessBegin(kolf);

		show_content_frames(s1, s2);

		show_content_frames(s3, s4);

		SetCurentCardURL(s5);
	}
}

function PrintSvod(CardURL) {
	if (CurentCardURL != '' && CurentCardURL != 'NO=')
		OpenURL(CardURL);
}

function OpenCardObj(CardURL, modeView) {
	if (CurentCardURL != '')
		OpenURL(CardURL + '&MODEVIEW=' + modeView);
}

function OpenDelCardObj(CardURL) {
	if (CurentCardURL != '') {
		if (confirm("Вы уверены, что хотите удалить объект?"))
			OpenURL(CardURL + '&MODEVIEW=del');
	}
}

function OpenAddCardObj(CardURL) {
	OpenURL(CardURL + '&MODEVIEW=add');
}

function iif(condition, var1, var2) {
	if (condition)
		return var1;
	else
		return var2;
}

function initTitleArray(TitleName, IDTitleName) {
	StrTitleArray = 'titles=[\'' + TitleName + '\'';
	IDTitleArray = 'IDtitles=[\'b' + IDTitleName + '\'';
	DoProcessBegin(1);
}

function AddToTitleArray(TitleName, IDTitleName) {
	var zpt = ',';
	if (StrTitleArray == '') {
		StrTitleArray = 'titles=[';
		IDTitleArray = 'IDtitles=[';
		zpt = '';
	}
	StrTitleArray = StrTitleArray + zpt + '\'' + TitleName + '\'';
	IDTitleArray = IDTitleArray + zpt + '\'b' + IDTitleName + '\'';
	DoProcessBegin(1);
}

function RefreshTitleTabs(n) {
	var xc = document.getElementById(ClassForTitles).innerHTML.split('<BR>');
	if (xc.length > 1) {
		var fT = 0;
		for ( var i = 0; i < xc.length; i++)
			if (xc[i].indexOf(IDtitles[n]) > -1) {
				fT = i;
				break;
			}

		if (fT < length - 1) {
			var s = '';
			for ( var i = 0; i < xc.length; i++)
				if (i != fT)
					s += xc[i] + '<BR>';

			s += xc[fT];
			document.getElementById(ClassForTitles).innerHTML = s;
		}
	}
}

var oldSelectedTab = 0;

function ViewTitleArray()
{   // var stdOut='';
    ColTabButtons = titles.length;
    var firstObj = '';

    var wTabs = document.getElementById(ClassForTitles).clientWidth - 20;
    var wStr = 0;
    var begT = 0;
    for ( var i = 0; i < ColTabButtons; i++) {
		if (IDtitles[i] == 'b')
			DoProcessEnd();
        else
        {
            var xxx = '<button onfocus="this.blur()" class="' + ((firstObj == '') ? 'TabButtonDw' : 'TabButtonUp') + '"  id="' + IDtitles[i] + '"' +
                      'onClick="selectTabPanel(' + i + ')"> ' + titles[i] + '</button>';
            if (firstObj == '')
            	oldSelectedTab = i;

            document.getElementById(ClassForTitles).insertAdjacentHTML("BeforeEnd", xxx);
            var oIDtitles = document.getElementById(IDtitles[i]);

            oIDtitles.style.width = oIDtitles.offsetWidth + 20;

            if (oIDtitles.offsetWidth < 80)
                oIDtitles.style.width = oIDtitles.offsetWidth + 60;

            wStr += oIDtitles.offsetWidth;

            isLast = i == ColTabButtons - 1;

            if (isLast || wStr > wTabs) {
                var ci = i - 1;
                if (!isLast)
                    wStr -= oIDtitles.offsetWidth;
                else
                    ci++;

                var sr = parseInt((wTabs - wStr) / (ci - begT + 1));

                var srx = wTabs - wStr - (sr * (ci - begT + 1));

                wStr = 0;
                xc = 0;
                for (var k = begT; k < ci + 1; k++)
                {
                	var okIDtitles = document.getElementById(IDtitles[k]);

                    okIDtitles.style.width = okIDtitles.offsetWidth + sr;
                    if (k == ci)
                        okIDtitles.style.width = okIDtitles.offsetWidth + srx;

                    xc += okIDtitles.offsetWidth;
                }

                //if (!isLast)
                if (IDtitles[ci + 1])
                    document.getElementById(IDtitles[ci + 1]).insertAdjacentHTML("beforeBegin", '<BR>');

                wStr = oIDtitles.offsetWidth;
                begT = i;
            }

            firstObj = '1';
        }
    }
    RefreshTitleTabs(1);
}

function ViewEnvTabArray() {
	ColTabButtons = tabNames.length;
	var firstObj = '';

	var xxx = '<button onfocus="this.blur()" class="'
			+ ((firstObj == '') ? 'TabButtonDw' : 'TabButtonUp') + '"  id="b'
			+ tabNames[i] + '" ' + 'onClick="selectEnvTabPanel(' + i + ')"> '
			+ tabNames[i] + '</button>';
	if (firstObj == '')
		oldSelectedTab = i;

	document.getElementById(ClassForTitles).insertAdjacentHTML("BeforeEnd", xxx);

	firstObj = '1';
}

function selectEnvTabPanel(n)
{
  if (oldSelectedTab != n)
  {
      var z = 3;

      document.getElementById(IDtitles[oldSelectedTab]).className = 'TabButtonUp';
      z = document.getElementById('tab'+tabNames[oldSelectedTab]).style.zIndex*1 + 1;

      document.getElementById('b' + tabNames[n]).className = 'TabButtonDw';
      document.getElementById('tab' + tabNames[n]).style.zIndex = z;

      oldSelectedTab = n;
  }

}function selectTabPanel(n)
{
  if (oldSelectedTab != n)
  {
      RefreshTitleTabs(n);
      var z = 3;

      document.getElementById(IDtitles[oldSelectedTab]).className = 'TabButtonUp';
      z = document.getElementById('ta'+IDtitles[oldSelectedTab]).style.zIndex*1 + 1;

      document.getElementById(IDtitles[n]).className = 'TabButtonDw';
      document.getElementById('ta'+IDtitles[n]).style.zIndex = z;

      oldSelectedTab = n;
  }
}

function removeClassName (elem, className) {
	elem.className = elem.className.replace(className, "").trim();
}

function addCSSClass (elem, className) {
	removeClassName (elem, className);
	elem.className = className;// (elem.className + " " + className).trim();
}

String.prototype.trim = function() {
	return this.replace( /^\s+|\s+$/, "" );
};

var oldClickRow = null;
var oldClickRowClass = "";

function ClickToRow(row,StyleClass) {
//	alert(oldClickRow.innerHTML)
	if (document.getElementById && document.getElementsByTagName) {
		if (!row) { return; }
		if (oldClickRow)
      addCSSClass(oldClickRow, oldClickRowClass);
    oldClickRow = row;
    oldClickRowClass = row.className;
		addCSSClass(row, StyleClass);
	}
}

function stripedTable(first) {
	if (document.getElementById && document.getElementsByTagName) {
		var allTables = document.getElementById('MFRowTable');
		if (!allTables) { return; }

				var trs = allTables.getElementsByTagName("tr");
				for (var j = 0; j < trs.length; j++) {
					removeClassName(trs[j], 'alternateRow');
					addCSSClass(trs[j], 'normalRow');
				}
				for (var k = 0; k < trs.length; k += 2) {
					removeClassName(trs[k], 'normalRow');
					addCSSClass(trs[k], 'alternateRow');
				}
    //    if (first>0) ClickToRow(trs[1],'selectRow');
    }
}

function ResizeFrame(IdTag,MaxSize) {
 if (document.getElementById(IdTag))
     resizeTo(parent.document.body.scrollWidth-DecFameWidth, document.getElementById(IdTag).height+5);
}

function ResizeToFrame100() {
	if (document.getElementById && document.getElementsByTagName) {
		var allframe = document.getElementsByTagName('iframe');
		if (!allframe)
			return;

		for ( var i = 0; i < allframe.length; i++)
			allframe[i].style.width = '100%';
	}
}

var RowsOfCurentTable = 0;

function resizeFrameTo(x, y) {
	resizeTo(x, y);
	if (document.getElementById("tableContainer"))
		tableContainer.style.height = y - MarginFrameHeight;
}

function ResizeFrameForTable() {
	if (pnp == 'environment') {
		if (EndOfTable) {
			var st = EndOfTable.offsetTop;
			var sb = 0;
			if (document.getElementById("EndOfButton"))
				sb = EndOfButton.offsetTop;
			var s = Math.max(st, sb);
			if (s < MaxFrameHeight)
				resizeFrameTo(parent.document.body.scrollWidth - DecFameWidth,
						s + MarginFrameHeightNoMax);
			else
				resizeFrameTo(parent.document.body.scrollWidth - DecFameWidth,
						MaxFrameHeight + MarginFrameHeight);
		}
	}
	if (pnp == 'query')
		ResizeFrameToEndOfTable();
}

function ResizeFrameForTableNoLimit() {
	if (pnp == 'environment')
		ResizeFrameToEndOfTable();
}

function ResizeFrameToEndOfTable() {

	if (EndOfTable) {
		var s = EndOfTable.offsetTop;
		resizeTo(parent.document.body.scrollWidth - DecFameWidth, s
				+ MarginFrameHeight);
		if (document.getElementById('tableContainer') != null)
			tableContainer.style.height = s + MarginFrameHeight;
	}
}

function ResizeFrameToHeight(s) {
	resizeTo(parent.document.body.scrollWidth - DecFameWidth, s
			+ MarginFrameHeight);
	if (document.getElementById('tableContainer') != null)
		tableContainer.style.height = s;
}

function ResizeFrameForGraph() {
	if (EndOfTable)
		resizeTo(parent.document.body.scrollWidth - DecFameWidth,
				EndOfTable.offsetTop);
}

function ParentNameOfPage() {
	var st = unescape(parent.location.href);
	if (st.indexOf("?") > 0)
		st = st.substring(0, st.indexOf("?"));
	st = st.substring(st.lastIndexOf("/") + 1, st.length);
	return st.substring(0, st.indexOf("."));
}

function CheckButtonForTable(name) {
	if ((name != null && name != '')
			|| (ParentNameOfPage() == 'cardobject' && parent.document
					.getElementById('b' + window.name))) {
		if (name == null || name == '')
			name = window.name;

		if (name != 'error500') {
			var allTables = document.getElementById('MFRowTable');// document.getElementById('scrollTable');

			if (!allTables)
				return;

			var nameB = parent.document.getElementById('b' + name);

			if (allTables.getElementsByTagName("td").length > 0) {
				if (nameB.value.substr(0, 3) != '   ')
					nameB.value = '   ' + nameB.value.trim();
				nameB.style.background = 'url("img\/checktab.gif") no-repeat left';
			} else
				nameB.style.background = '';
		} else {
			name = window.name;

			var nameB = parent.document.getElementById('b' + name);

			if (nameB) {
				if (nameB.value.substr(0, 3) != '   ')
					nameB.value = '   ' + nameB.value.trim();
				nameB.style.background = 'url("img\/badtab.gif") no-repeat left';
			}
		}
	}
}

function LoadInProcess(ClassForTitles) {
}
var KolOfRows = 0;

function expandFilterOfTable(state) {
	var whichEl = document.getElementById("filtertext0");
	if (!whichEl)
		return;

	var i = 0;
	while (whichEl) {
		if (state == 1)
			whichEl.style.display = "block";
		else {
			if (state == 0)
				whichEl.style.display = "none";
			else {
				if (whichEl.style.display == "block")
					whichEl.style.display = "none";
				else
					whichEl.style.display = "block";
			}
		}
		whichEl = document.getElementById("filtertext" + ++i);
	}
}

function SwapDisplayStatus(id1, type) {
	var whichEl = document.getElementById(id1);
	if (!whichEl)
		return;

	if (!type)
		type = 'block';

	if (whichEl.style.display == "" || whichEl.style.display == "none")
		whichEl.style.display = type;
	else
		whichEl.style.display = "none";
}

function FilterSubmit() {
	DoProcessBegin(1);
	var s = '';

	for ( var i = 0; i < document.form1.length; i++)
		if ((document.form1[i].name != '') && (document.form1[i].value != '')) {
			if (s != '')
				s += ',';

			s += document.form1[i].name + '='
					+ document.form1[i].value.toLocaleUpperCase();
		}

	if (s != '')
		document.form1.filter.value = encodeURIComponent(s);

}

function OpenURL(ahref) {
	var url =
		'<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">' +
		'<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">' +
		'<BASE href="'+location.href +'">' +
		'<link rel="stylesheet" href="css/style.css">' +
		'<style>#xScreenBox{display:table;}'+
		'#ProcessBox{display:inline-block;}</style>'+
		'<body style="background:ButtonFace" onLoad="location.href=\'' + ahref +'\'">' +
		'<div id=xScreenBox><div id=xScreenBoxContent>' +
		'<div id=ProcessBox class=ProcessBox><table class=ProcessTable>' +
		'<tr><td width=60 valign=middle><IMG  src=img/progress.gif></td><td><div style="margin:5px 40px 5px 0px;text-align:center;">' +
		'Пожалуйста подождите, идет загрузка данных...'+
		'</div></table></div></div></div>' +
		'</body>';

	var w=window.open("","","title,scrollbars,status,menubar");
		w.document.write(url);
		w.document.close();
}

function centerThat(id) {
	var obj = document.getElementById(id);
	if (obj) {
		obj.style.left = "50%";
		obj.style.top = "50%";
		obj.style.marginLeft = - obj.offsetWidth / 2;
		obj.style.marginTop = - obj.offsetHeight / 2;
	}
}


function DoProcessBegin(kolp) {
	if (parent.ProcessPoss.value < 0)
		DoProcessClose();
	parent.ProcessPoss.value = parent.ProcessPoss.value * 1 + kolp;
	parent.showModalForm('ProcessBox');
}

function DoProcessEnd() {
	if (parent.ProcessPoss) {
		parent.ProcessPoss.value = parent.ProcessPoss.value - 1;
		if (parent.ProcessPoss.value <= 0)
			DoProcessClose();
	}
}

function DoProcessClose() {
	if (parent.ProcessPoss)
		parent.ProcessPoss.value = 0;
	parent.hideModalForm('ProcessBox');
}

function ResizeQueryardObj() {
	if (parent.FullSizeCardObjQ && EndOfTable)
		resizeTo(parent.document.documentElement.clientWidth - DecFameWidth
				- 50, EndOfTable.offsetTop + MarginFrameHeight);

}

function HiddeTabsPanel() {
	if (parent.TabsPanel)
		parent.TabsPanel.style.display = 'none';
}

function CheckLenFormField(valueSetID, sizeField) {
	if (document.getElementById(valueSetID).value.length > sizeField) {
		alert('Поле не может быть длинее ' + sizeField + ' символов!');
		document.getElementById(valueSetID).value = document
				.getElementById(valueSetID).value.substring(0, sizeField - 1);
		return false;
	}
	return true;
}

function CheckNumberFormField(valueSetID) {
	var i = document.getElementById(valueSetID).value;
	if (i != '' && i != parseFloat(i)) {
		alert('Поле должно быть числовым!');
		return false;
	}
	return true;
}

function getAbsolutePos(el) {
	var r = {
		x : el.offsetLeft,
		y : el.offsetTop
	};

	if (el.offsetParent) {
		var tmp = getAbsolutePos(el.offsetParent);
		if (tmp.x > 0)
			r.x += tmp.x;
		if (tmp.y > 0)
			r.y += tmp.y;
	}
	return r;
}

function getOpener() {
	var i = 0;
	var op = null;
	if (openerStack.length == 0) {
		var p = opener;
		for (i = 0; p != null; i++) {
			openerStack[i] = p;
			try {
				p = p.top.window.opener;
			} catch (e) {
				p = null;
			}
		}
	}
	for (i = 0; i < openerStack.length; i++)
		if (!openerStack[i].closed) {
			op = openerStack[i];
			break;
		}

	return op;
}

function CloseGotoBackPage() {
	var op = getOpener();
	if (op != null) {
		window.close();
		op.focus();
	} else
		location.href = 'index.xhtm';
}

function GotoBackPage() {
	var op = getOpener();
	if (op != null)
		op.focus();
	else
		OpenURL("index.xhtm");
}

function OutSysMenu(dopField){
	try {
		if (gs_contextPath)
			return;
	} catch (e) {
	}
    if (!dopField)
		dopField = '';
	var op = getOpener();
	var t1;

	if (op != null)
		t1 = op.top.document.title;
	else
		t1 = 'меню';

     var x ="<DIV class=MenuBox id=MenuBox>\n" + dopField +
                    "<span title='Справка.' class=MenuButton onclick='OpenHelp()' style='margin-left:10px'><b>?</b></span>\n" +
                    "<span title='Переход к " + t1 + "' class=MenuButton onclick='GotoBackPage()'>◄</span>\n" +
                    "<span title='Закрыть и перейти к " + t1 + "' class=MenuButton onclick='CloseGotoBackPage()'>X</span>\n" +
                    "</DIV>\n";
    document.write(x);
}
// document.write('<script language="JavaScript" src="js/debug.js"></script>')
function OpenHelp() {
	var url = 'help/index.htm';
	if (envName != 'NONAME')
		url = 'help/envhelp.htm?n=' + envName;

	if (!windowHelp || windowHelp.closed)
		windowHelp = window.open(url, "",
				"title,scrollbars,status,menubar,resizable");
	else {
		windowHelp.location.href = url;
		windowHelp.focus();
	}

	return false;
}

function OutProcessBoxOpen(fr) {
	OutProcessMsgBox(fr, 'Пожалуйста подождите, идет загрузка данных...');
}

function OutProcessBox(fr) {
	OutProcessMsgBox(fr, 'Пожалуйста подождите, идет загрузка данных...');
}
var ProcessPoss;
function OutProcessMsgBox(fr, ms){
//    alert(document.body.clientHeight+'-'+document.documentElement.clientHeight+'-'+getElementComputedStyle(document.documentElement,'height'));
    document.write(
    '<div id=ProcessBox class="ProcessBox"><table class=ProcessTable>' +
    '<tr><td width="60" valign="middle"><IMG  src="img/progress.gif"></td><td><div style="margin:5px 40px 5px 0px;text-align:center;">' +
    ms );
    if (fr!=0)
       document.write('<br>осталось фреймов <input align=center size=2 style="color:red;width:15px;background-color:transparent;border:none;" type=text name=ProcessPoss id=ProcessPoss value=1>');
    document.write('</div></td></tr><tr><td colspan="2"><button onclick="DoProcessClose()">   закрыть   </button></td></tr></table></div>');
    ProcessPoss = document.getElementById('ProcessPoss');
}

function getElementComputedStyle(elem, prop) {
	if (typeof elem != "object")
		elem = document.getElementById(elem);

	// external stylesheet for Mozilla, Opera 7+ and Safari 1.3+
	if (document.defaultView && document.defaultView.getComputedStyle) {
		if (prop.match(/[A-Z]/))
			prop = prop.replace(/([A-Z])/g, "-$1").toLowerCase();
		return document.defaultView.getComputedStyle(elem, "")
				.getPropertyValue(prop);
	}

	// external stylesheet for Explorer and Opera 9
	if (elem.currentStyle) {
		var i;
		while ((i = prop.indexOf("-")) != -1)
			prop = prop.substr(0, i) + prop.substr(i + 1, 1).toUpperCase()
					+ prop.substr(i + 2);
		return elem.currentStyle[prop];
	}

	return "";
}

function SetOptSelected(o, v) {
	var ob1 = document.getElementById(o);
	if (ob1)
		for ( var i = 0; i < ob1.options.length; i++)
			if (v.indexOf(ob1.options[i].value) != -1)
				ob1.options[i].selected = i;
}

function getOutCardValue(v) {
	var o = v;
	if (o == 'F')
		o = 'Нет';
	else if (o == 'T')
		o = 'Да';
	else if (o == '01.01.1872')
		o = '';
	else if (o.indexOf('01.01.1872 ') != -1)
		o = o.substr(11);
	document.write(o);
}

function pause(mSec) {
	var clock = new Date();
	var justMinute = clock.getTime();
	var just;
	while (true) {
		just = new Date();
		if (just.getTime() - justMinute > mSec)
			break;
	}
}

function executeProcedure(nameProc, inParams, outParams, scriptProc) {
	var newItem = document.createElement("SCRIPT");
	newItem.charset = 'utf-8';
	newItem.src = 'go.xhtm?go=execproc&nameProc=' + nameProc + '&inParams='
			+ encodeURI(inParams) + '&outParams=' + outParams + '&scriptProc='
			+ scriptProc;
	document.body.appendChild(newItem);
}

function decodeHTMLText(s) {
	var newItem = document.createElement("P");
	// newItem.insertAdjacentHTML('afterBegin', s);
	newItem.innerHTML = s;
	newItem.innerHTML = newItem.innerText;
	return newItem.innerText;
	// return newItem.getAdjacentText('afterBegin');
}

function chkBlockMouseClick(e) {
	if (document.all)
		if (event.button == 2)  // Чтобы отключить левую кнопку поставьте цифру 1
			return false;
}

function setBlockMouseClick() {
	// document.onmousedown = chkBlockMouseClick;
	document.oncontextmenu = function(e) {
		return false;
	};
}

function LoadScript(name) {
	try {
		var newItem = document.createElement("SCRIPT");
		newItem.src = name;
		document.body.appendChild(newItem);
	} catch (e) {
		return 1;
	}
	return 0;
}

/*
 * function CheckNet() { var netcheck = (new Date()).getTime(); var r2 =
 * netcheck; LoadScript('js/netcheck.js'); // LoadScript('js/netcheck.js?r='+r2)
 * return (r1 != netcheck); }
 */
function ExecFile(s) {
	var WshShell;
	try {
		WshShell = new ActiveXObject("WScript.Shell");
		try {
			oExec = WshShell.Exec(s);

		} catch (e) {
			alert('Ошибка запуска программы ' + s + '!\n' + e.message);
		}
	} catch (e) {
		alert('Ошибка создания ActiveXObject!\n' + e.message);
	}
}

function formFocus(aForm) {
	if (aForm.elements[0] != null) {
		var max = aForm.length;
		for (var i = 0; i < max; i++) {
			if (aForm.elements[i].type == "text") {
				aForm.elements[i].focus();
				break;
			}
		}
	}
}

function setcookie(namecook, varcook) {
	var expiredate = new Date();

	expiredate.setDate(365 + expiredate.getDate());
	document.cookie = namecook + '=' + varcook + '; expires='
			+ expiredate.toGMTString() + ';';
}

function getcookie() {
	var cookielist = document.cookie.split('; ');
	var cookiearray = new Array();
	for ( var i = 0; i < cookielist.length; i++) {
		var name = cookielist[i].split('=');
		cookiearray[unescape(name[0])] = unescape(name[1]);
	}
	return cookiearray;
}

function getURLParameter(x) {
	var t, l, z, w, q, q1;
	t = window.location.search;
	l = t.length;
	if (l != 0)
		z = t.substring(1, l);
	else
		z = '';
	w = x + '=';
	q = z.indexOf(w);
	if (q != -1) {
		q = q + w.length;
		q1 = z.indexOf('%26', q);
		if (q1 == -1)
			q1 = z.indexOf('&', q);

		if (q1 == -1)
			q1 = z.length;

		return z.substring(q, q1);
	}
	return '';
}

function goOnSuccess(cod, url) {
	if (cod == '0')
		top.location.href = url;
	else
		alert('Rturn cod=' + cod);
}

var timeTickerArray = new Array();
var timeTickerTimer;
function ticker() {
	var curTime = new Date().getTime();
	for(var i=0 ; i < timeTickerArray.length; i++) {
		var outTime = Math.round((curTime - timeTickerArray[i].tickTime - deltaTime)/60000);
		if (outTime == 0)
			outTime = 'меньше 1';
		document.getElementById( timeTickerArray[i].tickID).innerHTML = outTime;
	 }
}

function reSizeImage(th){
	var url = th.src;//="temp/1040779.jpg

	if (url.indexOf('_tn.jpg') != -1)
		url = url.split('_tn.jpg')[0]+'.jpg';
	else
		url = url.split('.jpg')[0]+'_tn.jpg';

	th.src = url;
}

function LoadSound(name) {
	try {
		var newItem = document.createElement("embed");
		newItem.src = name;
		newItem.autostart = true;
		document.body.appendChild(newItem);
	} catch (e) {
		return 1;
	}
	return 0;
}

function isUploadImage(name) {
	return (name.toLowerCase().indexOf('.jpg') != -1)
			|| (name.toLowerCase().indexOf('.jpeg') != -1);
}

function reloadPage() {
	var url = location.href;
	if (location.href.indexOf('.xhtm?') == -1)
		url += '?';
	else
		url += '&';

	if (url.indexOf('?r=') != -1 || url.indexOf('&r=') != -1)
		url.replace('?r=', '?r=1').replace('&r=', '&r=1');
	else
		url += 'r=' + Math.random();

	location.href = url.replace('cmd=', 'xx=');
}

var oldCookScroll = '';

function showTinyPanel(){
	document.getElementById('tinyPanel').style.display='block';
	document.getElementById('tinyPanel').style.top = getBodyScrollTop() + 'px';
	if (oldCookScroll == '')
		oldCookScroll = cook['sgate-client-screen-scroll'];
	cook['sgate-client-screen-scroll'] = 'false';
	document.body.style.overflow = 'hidden';
}

function hideTinyPanel(){
	document.getElementById('tinyPanel').style.display='none';
	if (oldCookScroll != '') {
		cook['sgate-client-screen-scroll'] = oldCookScroll;
		oldCookScroll = '';
	}
	document.body.style.overflow = 'auto';
}

function globalEval( data ) {
	if ( data ) {
		// We use execScript on Internet Explorer
		// We use an anonymous function so that context is window
		// rather than jQuery in Firefox
		( window.execScript || function( data ) {
			window[ "eval" ].call( window, data );
		} )( data );
	}
}

function prepareReport() {
	var obb = document.getElementsByTagName('TABLE');
	for ( var i = 0; i < obb.length; i++)
		if (obb[i].getElementsByTagName('TD')[0].className.indexOf('r90') != -1) {
			var s = obb[i].style;
			var h = obb[i].offsetHeight;
			var w = obb[i].offsetWidth;
			var x = (h - w) / 2;
			s.top = x + 'px';
			s.left = -x + 'px';
			s.height = w + 'px';
			s.width = h + 'px';
		}
}

document.write('<div id=xTinyBackground style="display:none;"></div><table id=xScreenBox style="display:none;"><tr><td id=xScreenBoxContent></table>');

function showModalForm(formID){
	var $xScreenBoxContent = document.getElementById('xScreenBoxContent');
	document.getElementById('xTinyBackground').style.display = 'table';
	document.getElementById('xScreenBox').style.display = 'table';
	$xScreenBoxContent.innerHTML = '';
	document.getElementById(formID).style.display = 'inline-block';
	$xScreenBoxContent.appendChild(document.getElementById(formID));
}

function hideModalForm(formID){
	document.getElementById(formID).style.display = 'none';
	document.body.appendChild(document.getElementById(formID));
	document.getElementById('xTinyBackground').style.display = 'none';
	document.getElementById('xScreenBox').style.display = 'none';
}
//alert(1)