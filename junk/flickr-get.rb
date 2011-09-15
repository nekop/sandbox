#!/usr/bin/env ruby

require 'open-uri'

# http://www.flickr.com/photos/skrb/sets/72157627521767059/
## http://www.flickr.com/photos/skrb/6136733492/in/set-72157627521767059
# http://www.flickr.com/photos/skrb/6136733492/sizes/l/in/set-72157627521767059/
# http://farm7.static.flickr.com/6201/6136733492_760d119e02_b_d.jpg

flickr_username = "skrb"
flickr_set_id = "72157627521767059"
flickr_set_pages = 4
flickr_set_url = "http://www.flickr.com/photos/#{flickr_username}/sets/#{flickr_set_id}/"

PHOTO_ID_RE = %r|a href="/photos/#{flickr_username}/(\d+)/in/set-#{flickr_set_id}"|

photo_ids = []
photo_urls = []

(1..flickr_set_pages).each do |i|
  open("#{flickr_set_url}?page=#{i}") do |f|
    f.each do |l|
      if l =~ PHOTO_ID_RE then
        photo_ids << $1
      end
    end
  end
end


photo_ids.each do |photo_id|
  image_url_re = %r|a href="(http://farm.\.static\.flickr\.com/\d+/#{photo_id}_\w+_b_d\.jpg)"|
  url = "http://www.flickr.com/photos/skrb/#{photo_id}/sizes/l/in/set-#{flickr_set_id}/"
  open(url) do |f|
    f.each do |l|
      if image_url_re =~ l then
        photo_urls << $1
        break
      end
    end
  end
end
  
puts photo_urls

