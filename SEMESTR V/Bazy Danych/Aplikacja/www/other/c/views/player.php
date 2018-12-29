<?php

$link = $_GET['l'];
$version = $_GET['v'];

echo WatchModel::getDomen($link);
echo WatchModel::showIframe($link);