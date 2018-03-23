Array.matrix = function(numrows, numcols, initial) {
  var arr = [];
  for (var i = 0; i < numrows; ++i) {
    var columns = [];
    for (var j = 1; j <= numcols; ++j) {
      columns[j] = initial;
    }
    arr[i] = columns;
  }
  return arr;
}

function applyTheme(trackers, divId, color, type) {

  Highcharts.createElement('link', {
    href: 'https://fonts.googleapis.com/css?family=Signika:400,700',
    rel: 'stylesheet',
    type: 'text/css'
  }, null, document.getElementsByTagName('head')[0]);
  // Add the background image to the container
  var finalize = [];
  var final_data = Array.matrix(4, 24, 0);
  for (var j = 0; j < 96; j++) {
    var x = Math.floor(trackers.activeTime[j].min / 15) - 1;
    var y = Math.ceil(trackers.activeTime[j].hour);
    final_data[x][y] = Math.floor(trackers.activeTime[j].activeTime);
  }
  var count = 0;
    var total = 0;
    type='team';
    for (var i = 0; i < 24; i++) {
    	for (var j = 0, h = 4; j < 5; j++, h--) {
        if (j == 0) {
          finalize[count] = [];
          finalize[count][0] = i;
          finalize[count][1] = j;
          finalize[count][2] = final_data[0][i] + final_data[1][i] + final_data[2][i] + final_data[3][i];
          total = total + finalize[count][2];
          count++;
        } else {
          finalize[count] = [];
          finalize[count][0] = i;
          finalize[count][1] = j;
          finalize[count][2] = final_data[h][i];
          count++;
        }
      }
    }

  Highcharts.theme = {
    chart: {
      backgroundColor: null,
      style: {
        fontFamily: 'Signika, serif'
      }
    },
    title: {
      style: {
        color: 'black',
        fontSize: '16px',
        fontWeight: 'bold'
      }
    },
    credits: {
      enabled: false
    },
    subtitle: {
      style: {
        color: 'black'
      }
    },
    tooltip: {
      borderWidth: 0
    },
    legend: {
      itemStyle: {
        fontWeight: 'normal',
        fontSize: '13px'
      }
    },
    xAxis: {
      labels: {
        style: {
          color: '#6e6e70'
        }
      }
    },
    yAxis: {
      labels: {
        style: {
          color: '#6e6e70'
        }
      }
    },
    plotOptions: {
      series: {
        shadow: true
      },
      candlestick: {
        lineColor: '#404048'
      },
      map: {
        shadow: false
      }
    },
    // Highstock specific
    navigator: {
      xAxis: {
        gridLineColor: '#D0D0D8'
      }
    },
    rangeSelector: {
      buttonTheme: {
        fill: 'white',
        stroke: '#C0C0C8',
        'stroke-width': 1,
        states: {
          select: {
            fill: '#D0D0D8'
          }
        }
      }
    },
    scrollbar: {
      trackBorderColor: '#C0C0C8'
    },
    // General
    background2: '#E0E0E8'
  };
  // Apply the theme
  Highcharts.setOptions(Highcharts.theme);
  theme(finalize, divId, color, type);
}
function theme(finalize, divId, color, type) {
  if (type != 'employee') 
  {
	console.log(divId);
    Highcharts.chart(divId, {
      chart: {
        type: 'heatmap',
        marginTop: 80,
        marginBottom: 80,
        height: 300
      },
      title: {
        text: 'Hourwise: Active Time'
      },
      xAxis: {
    	title:{
    		enabled:true,
    		text:'hours',
    		style:{
    		fontWeight:"bold",
    		fontSize:"16px"}
    	},
        opposite: true,
        categories: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23']
      },
      yAxis: {
    	  title:{
      		enabled:true,
      		text:'mins',
      		style:{
        		fontWeight:"bold",
        		fontSize:"16px"}
      	},
        categories: ['Total', '45-60', '30-45', '15-30', '00-15']
      },
      colorAxis: {
        min: 0,
        max: 15,
        labels: {
          step: 2,
          enabled: true
        },
        minColor: '#ffffff',
        maxColor: color
      },
      legend: {
        align: 'center',
        layout: 'horizontal',
        margin: 0,
        verticalAlign: 'bottom',
        x: 10,
        symbolWidth: 320
      },
      tooltip: {
    	visible: false,
        formatter: function() {
          return this.point.value + '</b> mins active <br><b>';
        }
      },
      series: [{
        borderWidth: 1,
        data: finalize,
        dataLabels: {
          enabled: true,
          color: '000000',
          style: {
            fontWeight: 'normal',
            textShadow: 'none'
          }
        }
      }]
    });
  } else {
    Highcharts.chart(divId, {
      chart: {
        type: 'heatmap',
        marginTop: 80,
        marginBottom: 80,
        height: 300
      },
      title: {
        text: 'Hourwise: Active Time'
      },
      xAxis: {
        opposite: true,
        categories: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23']
      },
      yAxis: {
        categories: ['45-60', '30-45', '15-30', '00-15'],
        title: null
      },
      colorAxis: {
        min: 0,
        max: 15,
        labels: {
          step: 2,
          enabled: true
        },
        minColor: '#ffffff',
        maxColor: color
      },
      legend: {
        align: 'center',
        layout: 'horizontal',
        margin: 0,
        verticalAlign: 'bottom',
        x: 10,
        symbolWidth: 320
      },
      tooltip: {
        formatter: function() {
          return this.point.value + '</b> mins active <br><b>';
        }
      },
      series: [{
        name: 'Active time for employee',
        borderWidth: 1,
        data: finalize,
        dataLabels: {
          enabled: false,
          color: '000000',
          style: {
            textShadow: '2px'
          }
        }
      }]
    });
  }
}