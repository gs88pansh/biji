var bijiDeck = (function(){
  function bijiPoker(card, $el) {
    var transform = prefix('transform');
    var transition = prefix('transition');
    var transitionDelay = prefix('transitionDelay');

    card.bijiPoker = function (i, cards_num, len, cb) {
		
      var posArray = [{x: -60,y: 125,},{x: -360,y: 0,},{x: -210,y: -125,},{x: 90,y: -125,},{x: 240,y: 0,}];
      var delay = i * 100;
	  cards_num = cards_num/9;
	  
      var target = posArray[i%cards_num];
	  target.x = target.x + i/cards_num*20;
	  
      setTimeout(function () {
        $el.style.zIndex = len - 1 + i;
      }, delay);

      setTimeout(function () {
        $el.style[transition] = 'all .1s cubic-bezier(0.645, 0.045, 0.355, 1.000)';
        $el.style[transform] = translate(target.x + '%', target.y + '%');
      }, delay + 25);

      setTimeout(function () {
        $el.style[transition] = '';
        cb(i);
      }, delay + 100);
    };
  }

  function bijiPokerModule(deck) {
    deck.bijiPoker = deck.queued(bijiPoker);
	
    function bijiPoker(next) {
      var cards = deck.cards;
      var len = cards.length;
	  var cards_num = deck.people_num * 9;
	  
      cards.slice(-1*cards_num).reverse().forEach(function (card, i) {
        card.bijiPoker(i, cards_num, len, function (i) {
          if (i === cards_num-1) {
            next();
          }
        });
      });
    }
  }
  


  
  function bijiPokerOverModule(deck) {
    deck.bijiPokerOver = deck.queued(bijiPokerOver);
	
    function bijiPokerOver(next,A) {
		
	  deck.loadingWrapper = document.getElementById('loadingWrapper');
	  var width_middle = document.body.offsetWidth/2;
	  var height_bottom = document.body.offsetHeight;
	  var $el = document.createElement('div');
	  $el.classList.add('deck');
	  deck.loadingWrapper.appendChild($el);
	
	  var transform = Deck.prefix('transform');
	  for(var i=0;i<A[0].length;i++){
	
		var card = Deck.Card(A[0][i],true);
        card.mount($el);
		card.pokersClickChoose();
		card.$el.style.width = '63px';
		card.$el.style.height = '90px';
		var widthInTwoCards = 63*1.05;
		var leftCardOffset = width_middle-widthInTwoCards*4.5;
		var cardHeight = card.$el.getBoundingClientRect().height;
		card.$el.style.left = widthInTwoCards*i + leftCardOffset + 'px';
		card.$el.style.top = '220px';
		card.$el.style.transform = 'translate(0px,0px)';
		deck.my_cards.push(card);
	  }
	  deck.loadingWrapper.style.display = 'block';
	  next();
    }
  }

  
  function bijiSubmitModule(deck){
	deck.bijiSubmit = deck.queued(bijiSubmit);
	
    function bijiSubmit(next,A) {
	  for(var i=0;i<A[0].length;i++)
	    $(".cards-"+A[0][i]).show();
	  next();
    }
  }
  
  
  function bijiDeck(people_num, $c,face,loadingWrapper){
    var deck = Deck(false,face,70);
	
	var people_detail = [];
	
	var people_divs = [];
	
	for(var i=0;i<5;i++){
	  
	  var $people = document.createElement('div');
	  var $head = document.createElement('div');
	  var $person_id = document.createElement('div');
	  var $p = document.createElement('p');
	  var $span1 = document.createElement('span');
	  var $span2 = document.createElement('span');
	  var $spanMid = document.createElement('span');
	  var $img = document.createElement('img');
	  
	  $people.classList.add('person-mes', 'person-pos-'+i);
      $head.classList.add('person-head');
      $person_id.classList.add('person-caption');
	  $head.appendChild($img);
	  $img.setAttribute('src','css/img/head'+i+'.jpg')
	  
	  $p.appendChild($span1);
	  
	  $p.appendChild($spanMid);
	  $spanMid.innerHTML = '，';
	  $p.appendChild($span2);
	  
	  $person_id.appendChild($p);
	  
	  $people.appendChild($head);
	  $people.appendChild($person_id);
	  
	  
	  $c.appendChild($people);
	  
	  var people = {$people:$people,$head:$head,$person_id:$person_id,$p:$p,$span1:$span1,$span2:$span2,person_id:null,total_left:0,index:i};
	  people_detail.push(people);
	  
	}
	
	for(var i=0;i<deck.cards.length;i++){
		bijiPoker(deck.cards[i],deck.cards[i].$el);
	}
	deck.people_num = people_num;
	bijiPokerModule(deck);
	bijiPokerOverModule(deck);
	bijiSubmitModule(deck);
	
	deck.setTotalLeft = setTotalLeft;
	deck.addPerson = addPerson;
	
	deck.people_detail = people_detail;
	deck.loadingWrapper = loadingWrapper;
	deck.my_cards = []
	deck.first = []
	deck.second = []
	deck.third = []
	
	return deck;
	
	
	function setTotalLeft(person_id,total_left){
		for(var i=0;i<people_detail.length;i++){
			if(person_id == people_detail[i].person_id){
				people_detail[i].total_left = total_left;
				people_detail[i].$span2.innerHTML = total_left;
				return;
			}
		}
	}
	
	function addPerson(person_id){
		for(var i=0;i<people_detail.length;i++){
			if(people_detail[i].person_id === null){
				people_detail[i].person_id = person_id;
				people_detail[i].$span1.innerHTML = person_id;
				people_detail[i].$people.style.display = 'block';
				return;
			}
		}
	}
  }
  
  
  return bijiDeck;
})();
