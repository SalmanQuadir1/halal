$( document ).ready(function() {
var base_color = "rgb(230,230,230)";
var active_color = "rgb(237, 40, 70)";



var child = 1;
var length = $("section").length - 1;
$("#prev").addClass("disabled");
$("#submit").addClass("disabled");

$("section").not("section:nth-of-type(1)").hide();
$("section").not("section:nth-of-type(1)").css('transform','translateX(100px)');

var svgWidth = length * 200 + 24;
$("#svg_wrap").html(
  '<svg version="1.1" id="svg_form_time" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 ' +
    svgWidth +
    ' 24" xml:space="preserve"></svg>'
);

function makeSVG(tag, attrs) {
  var el = document.createElementNS("http://www.w3.org/2000/svg", tag);
  for (var k in attrs) el.setAttribute(k, attrs[k]);
  return el;
}

for (i = 0; i < length; i++) {
  var positionX = 12 + i * 200;
  var rect = makeSVG("rect", { x: positionX, y: 9, width: 200, height: 6 });
  document.getElementById("svg_form_time").appendChild(rect);
  // <g><rect x="12" y="9" width="200" height="6"></rect></g>'
  var circle = makeSVG("circle", {
    cx: positionX,
    cy: 12,
    r: 12,
    width: positionX,
    height: 6
  });
  document.getElementById("svg_form_time").appendChild(circle);
}

var circle = makeSVG("circle", {
  cx: positionX + 200,
  cy: 12,
  r: 12,
  width: positionX,
  height: 6
});
document.getElementById("svg_form_time").appendChild(circle);

$('#svg_form_time rect').css('fill',base_color);
$('#svg_form_time circle').css('fill',base_color);
$("circle:nth-of-type(1)").css("fill", active_color);

 
$(".button").click(function () {
  $("#svg_form_time rect").css("fill", active_color);
  $("#svg_form_time circle").css("fill", active_color);
  var id = $(this).attr("id");
  if (id == "next") {
    $("#prev").removeClass("disabled");
    if (child >= length) {
      $(this).addClass("disabled");
      $('#submit').removeClass("disabled");
    }
    if (child <= length) {
      child++;
    }
  } else if (id == "prev") {
    $("#next").removeClass("disabled");
    $('#submit').addClass("disabled");
    if (child <= 2) {
      $(this).addClass("disabled");
    }
    if (child > 1) {
      child--;
    }
  }
  var circle_child = child + 1;
  $("#svg_form_time rect:nth-of-type(n + " + child + ")").css(
    "fill",
    base_color
  );
  $("#svg_form_time circle:nth-of-type(n + " + circle_child + ")").css(
    "fill",
    base_color
  );
  var currentSection = $("section:nth-of-type(" + child + ")");
  currentSection.fadeIn();
  currentSection.css('transform','translateX(0)');
 currentSection.prevAll('section').css('transform','translateX(-100px)');
  currentSection.nextAll('section').css('transform','translateX(100px)');
  $('section').not(currentSection).hide();
});

});
/*signature pad script*/
 $('.sigPad').signaturePad({
        defaultAction :'drawIt',            
        penColour :'#145394',
        lineWidth : 0,
        drawOnly: true,
        validateFields: true    
      });    
      
      $('.sigPad').click(function(e){
            e.preventDefault();
            var instance = $('.sigPad').signaturePad();
            var sig = instance.getSignatureImage();
              $("#sig_image").val(sig); 
              //console.log(sig);
      });
      
      $("#clearSignature").click(function() {
        console.log("Cleared");
        $("#sig_image").val('');
      });
    //-------signature pad javascript ends here-----//
     
  
    $('#next').click(function(){
 	   $("html, body").animate({scrollTop:"0"},1000);

 });
 $('#prev').click(function(){
	   $("html, body").animate({scrollTop:"0"},1000);

 });
 /*Add & remove  form fields*/
  $(document).ready(function () {
  
  // Denotes total number of rows
  var rowIdx = 0;

  // jQuery button click event to add a row
  $('#addBtn').on('click', function () {

    // Adding a row inside the tbody.
    $('#tbody').append(`<tr id="R${++rowIdx}">
         <td class="row-index text-center">
         <input type="text"/>
         </td>
          <td class="text-center"><input type="text"/>
            
            </td>
              <td class="text-center"><input type="text"/>
            
            </td>
              <td class="text-center"><input type="text"/>
            
            </td>
            <td class="text-center">
            <button class="btn btn-danger remove"
                type="button">Remove</button>
              </td>
            
          </tr>`);
  });
  $('#tbody').on('click', '.remove', function () {
	  
	    // Getting all the rows next to the 
	    // row containing the clicked button
	    var child = $(this).closest('tr').nextAll();
	  
	    // Iterating across all the rows 
	    // obtained to change the index
	    child.each(function () {
	          
	        // Getting <tr> id.
	        var id = $(this).attr('id');
	  
	        // Getting the <p> inside the .row-index class.
	         var idx = $(this).children('.row-index').children(''); 
	  
	        // Gets the row number from <tr> id.
	        var dig = parseInt(id.substring(1));
	  
	        // Modifying row index.
	        idx.html(`Row ${dig - 1}`);
	  
	        // Modifying row id.
	        $(this).attr('id', `R${dig - 1}`);
	    });
	  
	    // Removing the current row.
	    $(this).closest('tr').remove();
	  
	    // Decreasing the total number of rows by 1.
	    rowIdx--;
	});

});
   
    
   
    $(document).ready(function () {
  	  
  	  // Denotes total number of rows
  	  var rowIdx = 0;

  	  // jQuery button click event to add a row
  	  $('#addBtn1').on('click', function () {

  	    // Adding a row inside the tbody.
  	    $('#tbody1').append(`<tr id="R${++rowIdx}">
  	         <td class="row-index text-center">
  	       
  	        <input type="text"/>
  	         </td>
  	          <td class="text-center"><input type="text"/>
  	            
  	            </td>
  	              <td class="text-center"><input type="text"/>
  	            
  	            </td>
  	              <td class="text-center"><input type="text"/>
  	            
  	            </td>
  	           
  	              <td class="text-center"><input type="text"/>
  	            
  	            </td>
  	            
  	              <td class="text-center"><input type="text"/>
  	            
  	            </td>
  	           
  	              <td class="text-center"><input type="text"/>
  	            
  	            </td>
  	           
  	              <td class="text-center"><input type="text"/>
  	            
  	            </td>
  	           
  	              <td class="text-center"><input type="text"/>
  	            
  	            </td>
  	          <td class="text-center">
              <button class="btn btn-danger remove"
                  type="button">Remove</button>
                </td>
  	          </tr>`);
  	  });
  	 $('#tbody1').on('click', '.remove', function () {
  		  
 	    // Getting all the rows next to the 
 	    // row containing the clicked button
 	    var child = $(this).closest('tr').nextAll();
 	  
 	    // Iterating across all the rows 
 	    // obtained to change the index
 	    child.each(function () {
 	          
 	        // Getting <tr> id.
 	        var id = $(this).attr('id');
 	  
 	        // Getting the <p> inside the .row-index class.
 	         var idx = $(this).children('.row-index').children(''); 
 	  
 	        // Gets the row number from <tr> id.
 	        var dig = parseInt(id.substring(1));
 	  
 	        // Modifying row index.
 	        idx.html(`Row ${dig - 1}`);
 	  
 	        // Modifying row id.
 	        $(this).attr('id', `R${dig - 1}`);
 	    });
 	  
 	    // Removing the current row.
 	    $(this).closest('tr').remove();
 	  
 	    // Decreasing the total number of rows by 1.
 	    rowIdx--;
 	});
 	 	});