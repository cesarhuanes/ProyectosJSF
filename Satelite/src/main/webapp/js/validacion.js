function valNumeros(evt) {
	evt = (evt) ? evt : event;
	var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : ((evt.which) ? evt.which : 0));
	var respuesta = true;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		respuesta = false;
	}
	return respuesta;
}

function valDecimales(evt) {
	evt = (evt) ? evt : event;
	var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : ((evt.which) ? evt.which : 0));
	var respuesta = true;
	if (charCode == 46) {
		respuesta = true;
	} else if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		respuesta = false;
	}
	return respuesta;
}

function valNumerosLetras(evt) {
	evt = (evt) ? evt : event;
	var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : ((evt.which) ? evt.which : 0));
	var respuesta = false;
	// charCode == 32 || (charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122)
	if (charCode == 8 || (charCode >= 48 && charCode <= 57) || (charCode >= 65 && charCode <= 90) || (charCode >= 96 && charCode <= 105)) {
		respuesta = true;
	}
	return respuesta;
}

function valNroComprobante(evt) {
	evt = (evt) ? evt : event;
	var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : ((evt.which) ? evt.which : 0));
	var respuesta = false;
	if (((charCode == 45) || (charCode >= 48 && charCode <= 57)) || (charCode < 31)) {
		respuesta = true;
	}
	return respuesta;
}

function soloDecimales(textbox, e) {
    //    var key = window.Event ? e.which : e.keyCode;
    var key = e.keyCode == 0 ? e.which : e.keyCode;
    var valor = document.getElementById(textbox).value;
    if ((key >= 48 && key <= 57) || (key == 8) || (key == 9)) {
        return true;
    }
    else if (key == 46 && valor.indexOf(".") == -1) {
        return true;
    }
    else {
        return false;
    }
}
