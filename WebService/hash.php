<?php
// Usage 1:
echo password_hash('rasmuslerdorf', PASSWORD_DEFAULT)."\n";
$passwordenc=password_hash('chahineB',PASSWORD_DEFAULT);
echo 'ChahineB is equal $passwordenc' ;
// $2y$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
// For example:
// $2y$10$.vGA1O9wmRjrwAVXD98HNOgsNpDczlqm3Jq7KnEd1rVAGv3Fykk1a

// Usage 2:
$options = [
  'cost' => 11
];
echo password_hash('rasmuslerdorf', PASSWORD_BCRYPT, $options)."\n";


// $2y$11$6DP.V0nO7YI3iSki4qog6OQI5eiO6Jnjsqg7vdnb.JgGIsxniOn4C

// See the password_hash() example to see where this came from.
 $hash = '$2y$07$BCryptRequires22Chrcte/VlQH0piJtjXl.0t1XkA8pw9dMXTpOq';

if (password_verify('rasmuslerdor', $hash)) {
    echo 'Password is valid!';
} else {
    echo 'Invalid password.';
} 