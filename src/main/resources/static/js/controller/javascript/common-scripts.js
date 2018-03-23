CommonUtils = function() {};
CommonUtils.checkDiff = function(start, end) {
  if (start && end) {
    var starttime = parseInt(start.substring(0, 2)) * 60 + parseInt(start.substring(3, 5));
    var endtime = parseInt(end.substring(0, 2)) * 60 + parseInt(end.substring(3, 5));
    return endtime - starttime;
  }
};
CommonUtils.computeDiff = function(startTime, endTime) {
  if (startTime && endTime) {
    var s_hr = startTime.split(":")[0] || 0;
    var s_min = startTime.split(":")[1] || 0;
    var e_hr = endTime.split(":")[0] || 0;
    var e_min = endTime.split(":")[1] || 0;
    return Math.abs(((parseInt(e_hr) - parseInt(s_hr)) * 60) + (parseInt(e_min) - parseInt(s_min)))
  }
};
CommonUtils.computeActiveTime = function(totalActiveTime) {
  var hour_data = totalActiveTime / 60; 
  var min_data = totalActiveTime % 60; 
  if (hour_data / 10 == 0) {  
    hour_data = "0" + hour_data; 
  } 
  if (min_data / 10 == 0) {  
    min_data = "0" + min_data; 
  } 
  return Math.floor(hour_data) + " hrs : " + Math.ceil(min_data) + " mins";
}
CommonUtils.computeInactiveTime = function(totalActiveTime) {
  var hour_data = totalActiveTime / 60; 
  var min_data = totalActiveTime % 60; 
  if (hour_data / 10 == 0) {  
    hour_data = "0" + hour_data; 
  } 
  if (min_data / 10 == 0) {  
    min_data = "0" + min_data; 
  } 
  return Math.floor(hour_data) + " hrs : " + Math.floor(min_data) + " mins";
}