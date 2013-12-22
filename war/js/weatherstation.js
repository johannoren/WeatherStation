/**
 * Scripts used by the Weather Station web site.
 */

var queryString = function() {
	// This function is anonymous, is executed immediately and
	// the return value is assigned to QueryString!
	var query_string = {};
	var query = window.location.search.substring(1);
	var vars = query.split("&");
	for (var i = 0; i < vars.length; i++) {
		var pair = vars[i].split("=");
		// If first entry with this name
		if (typeof query_string[pair[0]] === "undefined") {
			query_string[pair[0]] = pair[1];
			// If second entry with this name
		} else if (typeof query_string[pair[0]] === "string") {
			var arr = [ query_string[pair[0]], pair[1] ];
			query_string[pair[0]] = arr;
			// If third or later entry with this name
		} else {
			query_string[pair[0]].push(pair[1]);
		}
	}
	return query_string;
}();

function zeroPad(n) {
	if (n < 10) {
		n = "0" + "" + n;
	}
	return n;
}

function toHHMM(rawTime) {
	var d = new Date(rawTime);
	var m = d.getMinutes();
	var h = d.getHours();
	return zeroPad(h) + ":" + zeroPad(m);
}

function toYYMMDDHHMM(rawTime) {
	var d = new Date(rawTime);
	var m = zeroPad(d.getMinutes());
	var day = zeroPad(d.getDate());
	var h = zeroPad(d.getHours());
	var mo = zeroPad(d.getMonth() + 1);
	return d.getFullYear() + "-" + mo + "-" + day + " " + h + ":" + m;
}

function toYYYYMMDD(rawTime) {
	var d = new Date(rawTime);
	var day = zeroPad(d.getDate());
	var mo = zeroPad(d.getMonth() + 1);
	return d.getFullYear() + "" + mo + "" + day;
}

function toYYYYMMDDDashed(rawTime) {
	var d = new Date(rawTime);
	var day = zeroPad(d.getDate());
	var mo = zeroPad(d.getMonth() + 1);
	return d.getFullYear() + "-" + mo + "-" + day;
}

function millisToHumanTime(millis) {
	var secs = Math.floor(millis / 1000) % 60;
	var mins = Math.floor(millis / 60000) % 60;
	var hours = Math.floor(millis / 3600000) % 24;
	var days = Math.floor(millis / 86400000);

	var str = "";

	if (days > 0) {
		if (days === 1) {
			str = "1 dag ";
		} else {
			str = days + " dagar ";
		}
	}
	str = str + zeroPad(hours) + ":" + zeroPad(mins) + ":" + zeroPad(secs);
	return str;
}

function addDays(actualDate, change) {
	return new Date(actualDate.getFullYear(), actualDate.getMonth(), actualDate.getDate() + change); 
}

function getSwedishWeekdayName(epochTime) {
	var d = new Date(epochTime);
	var weekday = new Array(7);
	weekday[0] = "S&ouml;ndag";
	weekday[1] = "M&aring;ndag";
	weekday[2] = "Tisdag";
	weekday[3] = "Onsdag";
	weekday[4] = "Torsdag";
	weekday[5] = "Fredag";
	weekday[6] = "L&ouml;rdag";

	return weekday[d.getDay()];
}