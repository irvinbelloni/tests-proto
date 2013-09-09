var textEditAdministrator = "";
var textEditCandidate = "";

$(document).ready(function() {
	// Syntax highlighter
	SyntaxHighlighter.defaults['toolbar'] = false;
	SyntaxHighlighter.all();
	
	$(".datepicker" ).datepicker({ dateFormat: "dd/mm/yy" });
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