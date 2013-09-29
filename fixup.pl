#!/usr/bin/env perl

use strict;
use warnings;
use Data::Dumper;


sub main {
	my $files = `find . -print0`;
	my @files = split /\0/, $files;
	foreach my $old ( @files ) {
		my $new = $old;
		$new =~ s/\r$//;
		next if $old eq $new;
		next if $old =~ m/\.git\b/;
		system("mv \"$old\" \"$new\"\n");
	}
}
main();


