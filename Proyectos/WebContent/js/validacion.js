function valNumeros(evt){
	evt = (evt) ? evt : event;
	var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :((evt.which) ? evt.which:0));
	var respuesta = true;
	if(charCode >31 &&(charCode<48 || charCode>57)){
		respuesta=false;
	}
	return respuesta;
}

function valDecimales(evt){
	evt = (evt) ? evt : event;
	var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :((evt.which) ? evt.which:0));
	var respuesta = true;
	if(charCode == 46){
		respuesta=true;
	}else if(charCode > 31 && (charCode<48 || charCode>57)){
		respuesta=false;
	}
	return respuesta;
}

function valNumerosLetras(evt){
	evt = (evt) ? evt : event;
	var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :((evt.which) ? evt.which:0));
	var respuesta = false;
	//charCode == 32 || (charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122)
	if(charCode == 8 || (charCode >= 48 && charCode <= 57)|| (charCode >= 65 && charCode <= 90) || (charCode >= 96 && charCode <= 105)){
		respuesta=true;
	}
	return respuesta;
}

function valNroComprobante(evt) {
	evt = (evt) ? evt : event;
	var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :((evt.which) ? evt.which:0));
	var respuesta = false;
    if(((charCode == 45) || (charCode >= 48 && charCode <= 57))||(charCode < 31)){
    	respuesta = true;
    }
    return respuesta;
}


//---------------------------- NEW FUNCTIONS -----------------------

function valNumerosLetrasNoEspacio(evt)
{
	evt = (evt) ? evt : event;
	var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :((evt.which) ? evt.which:0));
	var respuesta = false;
	
	var numeros = (48 <= charCode && charCode <= 57);
	var abcMinus = (97 <= charCode && charCode <= 122);
	var abcMayus = (65 <= charCode && charCode <= 90);
	
	if (numeros || abcMinus || abcMayus || (8 == charCode))
	{
		respuesta = true;
	}
	return respuesta;
}

function valNumerosLetrasSiEspacio(evt)
{
	evt = (evt) ? evt : event;
	var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :((evt.which) ? evt.which:0));
	var respuesta = false;
	
	var numeros = (48 <= charCode && charCode <= 57);
	var abcMinus = (97 <= charCode && charCode <= 122);
	var abcMayus = (65 <= charCode && charCode <= 90);
	
	if (numeros || abcMinus || abcMayus || (8 == charCode) || (32 == charCode))
	{
		respuesta = true;
	}
	return respuesta;
}

function valLetrasNoEspacio(evt)
{
	evt = (evt) ? evt : event;
	var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :((evt.which) ? evt.which:0));
	var respuesta = false;
	
	var abcMinus = (97 <= charCode && charCode <= 122);
	var abcMayus = (65 <= charCode && charCode <= 90);
	
	if (abcMinus || abcMayus || (8 == charCode))
	{
		respuesta = true;
	}
	return respuesta;
}

function valLetrasSiEspacio(evt)
{
	evt = (evt) ? evt : event;
	var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :((evt.which) ? evt.which:0));
	var respuesta = false;
	
	var abcMinus = (97 <= charCode && charCode <= 122);
	var abcMayus = (65 <= charCode && charCode <= 90);
	
	if (abcMinus || abcMayus || (8 == charCode) || (32 == charCode))
	{
		respuesta = true;
	}
	return respuesta;
}

function valNumerosNoEspacio(evt)
{
	evt = (evt) ? evt : event;
	var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :((evt.which) ? evt.which:0));
	var respuesta = false;
	
	var numeros = (48 <= charCode && charCode <= 57);
	
	if (numeros || (8 == charCode))
	{
		respuesta = true;
	}
	return respuesta;
}

function valNumerosSiEspacio(evt)
{
	evt = (evt) ? evt : event;
	var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :((evt.which) ? evt.which:0));
	var respuesta = false;
	
	var numeros = (48 <= charCode && charCode <= 57);
	
	if (numeros || (8 == charCode) || (32 == charCode))
	{
		respuesta = true;
	}
	return respuesta;
}
