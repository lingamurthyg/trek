// CargoTrak client-side. jQuery 1.4 style. Don't add modern features.

(function() {
    function ready(fn) {
        if (document.readyState !== 'loading') fn();
        else document.addEventListener('DOMContentLoaded', fn);
    }

    ready(function() {
        // confirm-on-click for any element with [data-confirm]
        var els = document.querySelectorAll('[data-confirm]');
        for (var i = 0; i < els.length; i++) {
            (function(el) {
                el.addEventListener('click', function(e) {
                    if (!confirm(el.getAttribute('data-confirm') || 'Are you sure?')) {
                        e.preventDefault();
                    }
                });
            })(els[i]);
        }

        // Stale-session timeout warning. 28 minutes.
        setTimeout(function() {
            alert('Your session is about to expire. Please save your work.');
        }, 28 * 60 * 1000);
    });
})();
