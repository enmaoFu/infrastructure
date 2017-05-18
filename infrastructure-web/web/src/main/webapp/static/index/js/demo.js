/**
 * Particleground demo
 * @author Jonathan Nicol - @mrjnicol
 */

$(document).ready(function() {
  $('#particles').particleground({
    dotColor: '#37e3c2',
    lineColor: '#37e3c2'
  });
  $('.intro').css({
    'margin-top': -($('.intro').height() / 2)
  });
});