import datetime
from urllib.request import urlopen
from bs4 import BeautifulSoup


class VisiblePasses(object):
    def __init__(self, passes_url):
        soup = BeautifulSoup(urlopen(passes_url), 'html.parser')
        rows = soup.find_all('tr', {'class': 'clickableRow'})
        self.passes = [ PassData(r) for r in rows ]
    def __str__(self):
        return '\n'.join([str(i) for i in self.passes])


class PassData(object):
    DATE = 0
    BRIGHTNESS = 1
    START = 2
    HI = START + 3
    END = HI + 3

    def __init__(self, row):
        info = [ td.get_text() for td in row.findChildren('td')]
        self.date = info[self.DATE]
        self.brightness = info[self.BRIGHTNESS]
        self.start = PassDetails(info[self.START], info[self.START+1], info[self.START+2])
        self.hi = PassDetails(info[self.HI], info[self.HI+1], info[self.HI+2])
        self.end = PassDetails(info[self.END], info[self.END+1], info[self.END+2])

    def __str__(self):
        return "Pass data: " + str(self.date) + " " + \
                               str(self.brightness) + " " + \
                               str(self.start) + " " + \
                               str(self.hi) + " " + \
                               str(self.end)


class PassDetails(object):
    def __init__(self, t, h, a):
        [H,M,S] = list(map(int, t.split(':')))
        self.time = datetime.time(H,M,S)
        self.height = int(h[:-1])
        self.azimuth = a
    def __str__(self):
        return str(self.time) + " " + str(self.height) + " " + self.azimuth


class HAUrlBuilder(object):
    base = "http://www.heavens-above.com/PassSummary.aspx?"

    def build_standard_url(lat, lng, tz = "CET", sat = "25544"): #ISS
        return HAUrlBuilder.base + HAUrlBuilder.add_satid(sat) + HAUrlBuilder.add_lat(lat) + HAUrlBuilder.add_lng(lng) + HAUrlBuilder.add_tz(tz)
    def add_satid(sat):
        return "&add_satid=%s" % (sat)
    def add_lat(lat):
        return "&lat=%s" % (lat)
    def add_lng(lng):
        return "&lng=%s" % (lng)
    def add_tz(tz):
        return "&tz=%s" % (tz)


if __name__ == '__main__':
    tokyo_coords = {'lat': '35.6895', 'lng': '139.6917'}
    url = HAUrlBuilder.build_standard_url(tokyo_coords['lat'], tokyo_coords['lng'])
    print(url)
    print("### OUTPUT CHECK ###")
    v = VisiblePasses(url)
    print(v)
