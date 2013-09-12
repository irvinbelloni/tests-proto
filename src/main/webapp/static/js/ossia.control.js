var textEditAdministrator = "";
var textEditCandidate = "";

$(document).ready(function() {
	// Syntax highlighter
	SyntaxHighlighter.defaults['toolbar'] = false;
	SyntaxHighlighter.all();

	$(".datepicker").datepicker({
		dateFormat : "dd/mm/yy"
	});
});

function editProfil(admin, profilId, firstname, name, email, login) {
	$("#id").val(profilId);
	$("#prenom").val(firstname);
	$("#nom").val(name);
	$("#email").val(email);
	$("#login").val(login);
	$("#profilSubmit").val("Modifier");

	if (mode == "admin") {
		$(".side-form h2").html(textEditAdministrator);
	} else {
		$(".side-form h2").html(textEditCandidate);
	}
}

function editTestSheet (id, intitule, duree, type , text) {
	
//	$(".side-form input.type='submit'").val() ; 
	$("#testSheetId").val(id);
	$("#testSheetIntitule").val(intitule);
	$("#testSheetDuree").val(duree);
	$("#testSheetType").val(type);
}

$(function() {
	
//	$("#dialog-form").dialog(
//		{
//			autoOpen : false,
//			height : 300,
//			width  : 300,
//			modal  : true,
//			buttons : {
//				"Ajouter" : function() {
//					var bValid = true;
//					allFields.removeClass("ui-state-error");
//					if (bValid) {
//						$(this).dialog("close");
//					}
//				},
//				Cancel : function() {
//					$(this).dialog("close");
//				}
//			},
//			close : function() {
//				allFields.val("").removeClass("ui-state-error");
//			}
//		});
//	$("#createQuestion").button().click(function() {
//		$("#dialog-form").dialog("open");
//	});
	
	
});