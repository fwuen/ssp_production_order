$(document).ready(function() {
   $.get("rest/production").done(function(data) {
       $.each(data, function(key, value) {
           tstamp = new Date(value.prTimestamp)
           var prTime = ('0' + tstamp.getDate()).slice(-2) + '.'
               + ('0' + (tstamp.getMonth() + 1)).slice(-2) + '.'
               + tstamp.getFullYear() + ' ' + tstamp.getHours() + ':' + tstamp.getMinutes();
           $("#productionList tr:last").after(
               "<tr>" +
               "<td>" + value.prId + "</td>" +
               "<td>" + value.toolId + "</td>" +
               "<td>" + value.machineId + "</td>" +
               "<td>" + prTime + "</td>" +
               "<td>" + value.productionOrderByProductionOrderId.poId + "</td>" +
               "<td>" + value.productByProductId.pId + "</td>"
               + "</tr>")
       })
   })
});