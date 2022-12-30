$('.lerongba-nav-popup-toggle').click(function(){
    $('body').toggleClass('nav-popup-modal-open');
});

tinymce.init({
  selector: 'textarea#rawContent'
});

let page = 1;
function loadMorePosts() {
  const xhr = new XMLHttpRequest();
  let page = 1;
  xhr.open('GET', '/blog/more?page=' + page, true);
  xhr.onload = function() {
    if (this.status === 200) {
      page++;
      const posts = JSON.parse(this.responseText);
      if (posts.length > 0) {
        posts.forEach(function(post) {
          const blogPost = document.createElement('article');
          blogPost.innerHTML = `
            <img src="/images/blog_1.jpg" alt="blog post" class="blog-post-thumbnail">
            <div class="media-body">
              <h5 class="blog-post-title">${post.title}</h5>
              <p class="blog-post-excerpt">${post.excerpt}</p>
              <a href="/blog/posts/${post.postId}" class="blog-post-link link-hover-fx">Read more</a>
            </div>
          `;
          blogPost.classList.add('media', 'blog-post', 'wow', 'fadeInUp');
          document.getElementById('blogPosts').insertBefore(blogPost, document.getElementById('load-more'));
        });
      } else {
        document.getElementById('load-more').disabled = true;
      }
    }
  };
  xhr.send();
}



$('.main-nav .nav-link[href^="/home"]:not([href="#!"])').click(function(){
    $('body').removeClass('nav-popup-modal-open');
});
$myCarousel = $('.carousel');

function doAnimations(elems) {
    var animEndEv = 'webkitAnimationEnd animationend';
  
    elems.each(function () {
      var $this = $(this),
          $animationType = $this.data('animation');
  
      // Add animate.css classes to
      // the elements to be animated
      // Remove animate.css classes
      // once the animation event has ended
      $this.addClass($animationType).one(animEndEv, function () {
        $this.removeClass($animationType);
      });
    });
  }
  
  // Select the elements to be animated
  // in the first slide on page load
  var $firstAnimatingElems = $myCarousel.find('.carousel-item:first')
    .find('[data-animation ^= "animated"]');
  
  // Apply the animation using the doAnimations()function
  doAnimations($firstAnimatingElems);
  
  // Attach the doAnimations() function to the
  // carousel's slide.bs.carousel event
  $myCarousel.on('slide.bs.carousel', function (e) {
    // Select the elements to be animated inside the active slide
    var $animatingElems = $(e.relatedTarget)
      .find("[data-animation ^= 'animated']");
    doAnimations($animatingElems);
  });

  function doAnimations(elems) {
    var animEndEv = 'webkitAnimationEnd animationend';
  
    elems.each(function () {
      var $this = $(this),
          $animationType = $this.data('animation');
  
      // Add animate.css classes to
      // the elements to be animated
      // Remove animate.css classes
      // once the animation event has ended
      $this.addClass($animationType).one(animEndEv, function () {
        $this.removeClass($animationType);
      });
    });
  }
  
  // Select the elements to be animated
  // in the first slide on page load
  var $firstAnimatingElems = $myCarousel.find('.carousel-item:first')
    .find('[data-animation ^= "animated"]');
  
  // Apply the animation using the doAnimations()function
  doAnimations($firstAnimatingElems);
  
  // Attach the doAnimations() function to the
  // carousel's slide.bs.carousel event
  $myCarousel.on('slide.bs.carousel', function (e) {
    // Select the elements to be animated inside the active slide
    var $animatingElems = $(e.relatedTarget)
      .find("[data-animation ^= 'animated']");
    doAnimations($animatingElems);
  });