$(function () {
    new Chart(document.getElementById("line_chart").getContext("2d"), getChartJs('line'));
});

function getChartJs(type) {
    var config = null;

    if (type === 'line') {
        config = {
            type: 'line',
            data: {
                labels: ["January", "February", "March", "April", "May", "June", "July"],
                datasets: [{
                    label: "My First dataset",
                    data: [28, 32, 39, 37, 42, 55, 50],
                    borderColor: 'rgba(102,165,226, 0.2)',
                    backgroundColor: 'rgba(102,165,226, 0.7)',
                    pointBorderColor: 'rgba(102,165,226, 0.5)',
                    pointBackgroundColor: 'rgba(102,165,226, 0.2)',
                    pointBorderWidth: 1
                }, {
                    label: "My Second dataset",
                    data: [40, 48, 50, 48, 63, 62, 71],                    
                    borderColor: 'rgba(140,147,154, 0.2)',
                    backgroundColor: 'rgba(140,147,154, 0.2)',
                    pointBorderColor: 'rgba(140,147,154, 0)',
                    pointBackgroundColor: 'rgba(140,147,154, 0.9)',
                    pointBorderWidth: 1
                }]
            },
            options: {
                responsive: true,
                legend: false,
                
            }
        }
    }   
    return config;
}