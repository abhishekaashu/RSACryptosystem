#include <iostream>
#include <string>
#include <map>
#include "InfInt.h"
using namespace std;

#define lli long long int
#define pii pair<InfInt, InfInt>
#define ppi pair<pii, InfInt>

InfInt mod_add(const InfInt &x, const InfInt &y, const InfInt &m) {
	return (x+y)%m;
}

InfInt mod_mult(const InfInt &x, const InfInt &y, const InfInt &m) {
	return (x*y)%m;
}

InfInt mod_exp(InfInt x, InfInt y, const InfInt &m) {
	InfInt ans = 1;
	while (y > 0) {
		if (y%2 == 1) ans *= x;
		x = (x*x)%m;
		y /= 2;
	}
	return ans%m;
}

InfInt gcd(const InfInt &x, const InfInt &y) {
	if (y == 0) return x;
	else return gcd(y, x%y);
}

pii gcd_extended(const InfInt &x, const InfInt &y) {
	if (x == 0) return pii(0, 1);
	pii sub = gcd_extended(y%x, x);
	return pii(sub.second-((y/x)*sub.first), sub.first);
}

InfInt mod_inv(const InfInt &x, const InfInt &m) { // Assumes inverse exists i.e. gcd(x, m) != 1
	pii coeff = gcd_extended(x, m);
	return (coeff.first%m + m)%m;
}

string gen_rand(const lli &l) { // Generates random numbers of length l
	string s(l, 0);
	srand (time(NULL));
	for (auto &e : s) {
		e = (rand() % 10) + '0';
	}
	return s;
}

bool miller_rabin(const InfInt &n, const lli &l, const lli &k) {
	lli r = 0;
	InfInt d = n-1;
	while (d%2 == 0) {
		r++; d /= 2;
	}
	lli i=0;
	while (i<=k) {
		here: i++;
		InfInt a(gen_rand(l-1)); a = a%n + 2;
		InfInt x = mod_exp(a, d, n);
		if (x == 1 || x == n-1) continue;
		for (lli j=0; j<r-1; j++) {
			x = mod_exp(x, 2, n);
			if (x == 1) return false;
			if (x == n-1) goto here;
		}
		return false;
	}
	return true;
}

InfInt gen_prime(const lli &l) { // Generates random prime numbers of length l
	string s = gen_rand(l);
	s[l-1] = (rand()%5)*2 + '1';
	InfInt p(s);
	while (!miller_rabin(p, l, 100)){
		p += 2;
	}
	return p;
}

ppi rsa_keygen(const lli &l) {
	InfInt p = gen_prime(l), q = gen_prime(l), n;
	while (p == q) q = gen_prime(l);
	n = p*q;
	InfInt totient = (p-1)*(q-1);
	lli t = 8;
	InfInt e = gen_rand(min(t, l));
	do {
		e = gen_rand(min(t, l));
	} while(gcd(totient, e) != 1 && e < n);
	InfInt d = mod_inv(e, totient);
	return ppi(pii(n, e), d);
}

InfInt rsa_encrypt(const InfInt &m, const InfInt &e, const InfInt &n) {
	return mod_exp(m, e, n);
}

InfInt rsa_decrypt(const InfInt &c, const InfInt &d, const InfInt &n) {
	return mod_exp(c, d, n);
}

void rsa_test(const InfInt &m, const int &k) {
	ppi x = rsa_keygen(k);
	cout << x.first.first << " " << x.first.second << " " << x.second << endl;
	InfInt enc = rsa_encrypt(m, x.first.second, x.first.first);
	InfInt dec = rsa_encrypt(enc, x.second, x.first.first);
	cout << enc << " " << dec << endl;
}

InfInt sqrt(InfInt x) {
	InfInt ans = "1", y;
	while (y - ans >= 1 || ans - y >= 1) {
		y = ans;
		ans = (ans + x/ans)/2;
	}
	return ans;
}

InfInt baby_giant(InfInt a, InfInt b, InfInt m) {
	map<InfInt, InfInt> f1;
    InfInt n = sqrt(m) + 1;
    for (InfInt i=1; i<=n; i++)
    	f1[mod_exp(a, i*n, m)] = i;

    for (InfInt i=0; i<=n; i++) {
        InfInt cur = (mod_exp(a, i, m)*b)%m;
        if (f1.find(cur) != f1.end()) {
            InfInt ans = f1[cur]*n-i;
            if (ans < m) return ans;
        }
    }
    return "-1";
}

int main() {
	char c;
	cout << "Select the action you want to do\n";
	cout << "	a. Demonstrate RSA Cryptosystem\n";
	cout << "	b. Demonstrate Baby-step Giant-step Algorithm\n";
	cout << "	q. To quit the program\n\n";
	cout << "Enter the key: "; cin >> c;
	while (c != 'q') {
		if (c == 'a') {
			InfInt x;
			lli val;
			cout << "\nDemonstrating RSA Encryption!\n";
			cout << "Enter a number string to be encrypted: "; cin >> x;
			cout << "Select the Encryption Strength: "; cin >> val;
			ppi p = rsa_keygen(val);
			InfInt n = p.first.first, e = p.first.second, d = p.second;
			InfInt encrypt = rsa_encrypt(x, e, n);
			InfInt decrypt = rsa_decrypt(encrypt, d, n);
			cout << "Public Key is: " << e << "\n";
			cout << "Private Key is: " << d << "\n";
			cout << "The value of n used is: " << n << "\n";
			cout << "Encrypted string using RSA Encryption: " << encrypt << "\n";
			cout << "Decrypted string using RSA Decryption: " << decrypt << "\n\n";
		}
		else if (c == 'b') {
			InfInt a, b, m;
			cout << "\nDemonstrating Baby-step Giant-step Algorithm!\n"
			cout << "Enter a, b, m for which you want x such that a^x = b(mod m): ";
			cin >> a >> b >> m;
			cout << baby_giant(a, b, m) << "\n\n";
		}
		else {
			cout << "Failed! Please enter a correct code!\n\n";
		}
		cout << "Select the action you want to do\n";
		cout << "	a. Demonstrate RSA Cryptosystem\n";
		cout << "	b. Demonstrate Baby-step Giant-step Algorithm\n";
		cout << "	q. To quit the program\n\n";
		cout << "Enter the key: "; cin >> c;
	}
}