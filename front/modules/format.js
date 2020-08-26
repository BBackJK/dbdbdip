export default function FormatDate(date) {
  const formatDate = date.split('T');

  const ymd = formatDate[0];

  const beforeHms = formatDate[1].split('.')[0];

  const beforeHour = beforeHms.substring(0, 2);

  let afterHour = beforeHour * 1 + 9;

  if (afterHour >= 24) {
    afterHour -= 24;
  }

  if (afterHour < 10) {
    afterHour = '0' + afterHour;
  }

  const afterHms = (afterHour += ':' + beforeHms.substring(3));

  const fullyDate = ymd + ' ' + afterHms;

  return fullyDate;
}
