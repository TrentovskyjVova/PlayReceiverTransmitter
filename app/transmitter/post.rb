require 'net/http'
require 'json'
require 'uri'
require 'openssl'

class String
  def encrypt(key, iv)
    cipher = OpenSSL::Cipher::AES128.new(:CBC).encrypt
    cipher.key = key
    cipher.iv = iv
    s = cipher.update(self) + cipher.final

    s.unpack('H*')[0]
  end

  def decrypt(key, iv)
    cipher = OpenSSL::Cipher::AES128.new(:CBC).decrypt
    cipher.key = key
    cipher.iv = iv
    s = [self].pack("H*")

    cipher.update(s) + cipher.final
  end
end

key = '1231231231231232'
iv = 'RandomInitVector'

uri = URI('http://localhost:9000/receiver')
req = Net::HTTP::Post.new(uri, 'Content-Type' => 'text/plain')

req.body = {id: 1287, message: 'some awesome message'}.to_json.encrypt(key, iv)
res = Net::HTTP.start(uri.host, uri.port,
  :use_ssl => uri.scheme == 'https') do |http|
  http.request(req)
end

puts res.body.decrypt(key, iv)