$(document).ready(function() {
   $.get("rest/production").done(function(data) {
       $.each(data, function(key, value) {
           $("#productionList tr:last").after(
               "<tr>" +
               "<td>" + value.prId + "</td>" +
               "<td>" + value.toolId + "</td>" +
               "<td>" + value.machineId + "</td>" +
               "<td>" + new Date(value.prTimestamp) + "</td>" +
               "<td>" + value.productionOrderByProductionOrderId.poId + "</td>" +
               "<td>" + value.productByProductId.pId + "</td>"
               + "</tr>")
       })
   })
});